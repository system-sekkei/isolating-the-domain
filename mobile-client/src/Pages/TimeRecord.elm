module Pages.TimeRecord exposing (Model, Msg, init, update, view)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Pages.TimeRecord.Rules.BreakTimeCapRule as BreakTimeCapRule exposing (BreakTimeCapRule(..))
import Pages.TimeRecord.Rules.WorkTimeRangeRule as WorkingTimeRangeRule exposing (WorkTimeRangeRule(..))
import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute(..))
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour(..))
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute(..))
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute(..))
import Pages.TimeRecord.Types.PostedStatus exposing (PostedStatus)
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour(..))
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute(..))
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName(..))
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber(..))
import Types.Message as Message exposing (Message(..))
import Types.Time.WorkDate as WorkDate exposing (WorkDate(..))
import URLs



-- MODEL


type alias Model =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , state : PageState
    }


type PageState
    = Initializing
    | Editing PreparedTimeRecordForm TimeRecordForm ErrorMessages


type alias ErrorMessages =
    List Message


init : EmployeeNumber -> WorkDate -> ( Model, Cmd Msg )
init employeeNumber workDate =
    ( Model employeeNumber workDate Initializing, getPreparedTimeRecordForm employeeNumber workDate )



-- UPDATE


type Msg
    = PrepareForm (Result Http.Error PreparedTimeRecordForm)
    | EditForm TimeRecordForm
    | PostForm


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        PrepareForm result ->
            case result of
                Ok prepared ->
                    ( { model | state = Editing prepared prepared.preparedRequest [] }, Cmd.none )

                Err error ->
                    Debug.todo (Debug.toString error)

        EditForm newEditingForm ->
            case model.state of
                Editing prepared _ _ ->
                    let
                        validatedForm =
                            TimeRecordForm
                                newEditingForm.employeeNumber
                                (WorkDate.validate newEditingForm.workDate)
                                (StartHour.validate newEditingForm.startHour)
                                (StartMinute.validate newEditingForm.startMinute)
                                (EndHour.validate newEditingForm.endHour)
                                (EndMinute.validate newEditingForm.endMinute)
                                (DaytimeBreakMinute.validate newEditingForm.daytimeBreakTime)
                                (MidnightBreakMinute.validate newEditingForm.midnightBreakTime)

                        newErrorMessages =
                            List.filter (\err -> Message.isNotEmpty err)
                                [ WorkingTimeRangeRule
                                    validatedForm.startHour
                                    validatedForm.startMinute
                                    validatedForm.endHour
                                    validatedForm.endMinute
                                    |> WorkingTimeRangeRule.validate
                                , BreakTimeCapRule
                                    validatedForm.startHour
                                    validatedForm.startMinute
                                    validatedForm.endHour
                                    validatedForm.endMinute
                                    validatedForm.daytimeBreakTime
                                    validatedForm.midnightBreakTime
                                    |> BreakTimeCapRule.validate
                                ]
                    in
                    ( { model | state = Editing prepared validatedForm newErrorMessages }, Cmd.none )

                _ ->
                    ( model, Cmd.none )

        PostForm ->
            case model.state of
                Editing _ editing _ ->
                    --                    TODO dirty check
                    --                    TODO post request
                    --                    TODO server error handling
                    Debug.todo (Debug.toString editing)

                _ ->
                    ( model, Cmd.none )



-- VIEW


view : Model -> Html Msg
view model =
    div []
        [ h1 [] [ text "勤務時間の入力" ]
        , case model.state of
            Initializing ->
                div []
                    [ text "Now Loading..." ]

            Editing prepared editing errorMessages ->
                div []
                    [ errorMessageArea errorMessages
                    , timeRecordForm prepared editing
                    ]
        ]


errorMessageArea : ErrorMessages -> Html Msg
errorMessageArea errorMessages =
    section []
        [ ul []
            (List.map messageLine errorMessages)
        ]


messageLine : Message -> Html Msg
messageLine message =
    li [] [ text (message |> Message.toString) ]


timeRecordForm : PreparedTimeRecordForm -> TimeRecordForm -> Html Msg
timeRecordForm prepared editing =
    section []
        [ div []
            [ label [] [ text "氏名" ]
            , select
                [ onChange (\str -> EditForm { editing | employeeNumber = EmployeeNumber.parse str }) ]
                (List.map (employeeOption editing) prepared.contractingEmployees)
            ]
        , div []
            [ label [] [ text "勤務日" ]
            , input
                [ type_ "date"
                , value (editing.workDate |> WorkDate.toString)
                , onInput (\str -> EditForm { editing | workDate = DirtyWorkDate str })
                ]
                []
            , case editing.workDate of
                InvalidWorkDate err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            ]
        , div []
            [ label [] [ text "開始時刻" ]
            , input
                [ type_ "number"
                , value (editing.startHour |> StartHour.toString)
                , onInput (\str -> EditForm { editing | startHour = DirtyStartHour str })
                ]
                []
            , case editing.startHour of
                InvalidStartHour err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            , input
                [ type_ "number"
                , value (editing.startMinute |> StartMinute.toString)
                , onInput (\str -> EditForm { editing | startMinute = DirtyStartMinute str })
                ]
                []
            , case editing.startMinute of
                InvalidStartMinute err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            ]
        , div []
            [ label [] [ text "終了時刻" ]
            , input
                [ type_ "number"
                , value (editing.endHour |> EndHour.toString)
                , onInput (\str -> EditForm { editing | endHour = DirtyEndHour str })
                ]
                []
            , case editing.endHour of
                InvalidEndHour err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            , input
                [ type_ "number"
                , value (editing.endMinute |> EndMinute.toString)
                , onInput (\str -> EditForm { editing | endMinute = DirtyEndMinute str })
                ]
                []
            , case editing.endMinute of
                InvalidEndMinute err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            ]
        , div []
            [ label [] [ text "休憩時間" ]
            , input
                [ type_ "number"
                , value (editing.daytimeBreakTime |> DaytimeBreakMinute.toString)
                , onInput (\str -> EditForm { editing | daytimeBreakTime = DirtyDaytimeBreakMinute str })
                ]
                []
            , case editing.daytimeBreakTime of
                InvalidDaytimeBreakMinute err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            ]
        , div []
            [ label [] [ text "休憩時間（深夜）" ]
            , input
                [ type_ "number"
                , value (editing.midnightBreakTime |> MidnightBreakMinute.toString)
                , onInput (\str -> EditForm { editing | midnightBreakTime = DirtyMidnightBreakMinute str })
                ]
                []
            , case editing.midnightBreakTime of
                InvalidMidnightBreakMinute err _ ->
                    text (err |> Message.toString)

                _ ->
                    text ""
            ]
        , button [ onClick PostForm ] [ text "登録" ]
        ]


employeeOption : TimeRecordForm -> Employee -> Html msg
employeeOption editing employee =
    option
        [ value (employee.employeeNumber |> EmployeeNumber.toString)
        , selected (employee.employeeNumber == editing.employeeNumber)
        ]
        [ text (employee.employeeName |> EmployeeName.toString) ]


onChange : (String -> msg) -> Attribute msg
onChange handler =
    Html.Events.on "change" (Json.Decode.map handler Html.Events.targetValue)


onClick : msg -> Attribute msg
onClick msg =
    Html.Events.on "click" (Json.Decode.succeed msg)



-- HTTP


type alias PreparedTimeRecordForm =
    { contractingEmployees : List Employee
    , preparedRequest : TimeRecordForm
    }


type alias Employee =
    { employeeNumber : EmployeeNumber
    , employeeName : EmployeeName
    }


type alias TimeRecordForm =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , startHour : StartHour
    , startMinute : StartMinute
    , endHour : EndHour
    , endMinute : EndMinute
    , daytimeBreakTime : DaytimeBreakMinute
    , midnightBreakTime : MidnightBreakMinute
    }


preparedTimeRecordFormDecoder : Json.Decode.Decoder PreparedTimeRecordForm
preparedTimeRecordFormDecoder =
    Json.Decode.succeed PreparedTimeRecordForm
        |> Json.Decode.Pipeline.required "contractingEmployees" (Json.Decode.list employeeDecoder)
        |> Json.Decode.Pipeline.required "preparedRequest" timeRecordFormDecoder


employeeDecoder : Json.Decode.Decoder Employee
employeeDecoder =
    Json.Decode.succeed Employee
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "employeeName" EmployeeName.decoder


timeRecordFormDecoder : Json.Decode.Decoder TimeRecordForm
timeRecordFormDecoder =
    Json.Decode.succeed TimeRecordForm
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "workDate" WorkDate.decoder
        |> Json.Decode.Pipeline.required "startHour" StartHour.decoder
        |> Json.Decode.Pipeline.required "startMinute" StartMinute.decoder
        |> Json.Decode.Pipeline.required "endHour" EndHour.decoder
        |> Json.Decode.Pipeline.required "endMinute" EndMinute.decoder
        |> Json.Decode.Pipeline.required "daytimeBreakTime" DaytimeBreakMinute.decoder
        |> Json.Decode.Pipeline.required "midnightBreakTime" MidnightBreakMinute.decoder


getPreparedTimeRecordForm : EmployeeNumber -> WorkDate -> Cmd Msg
getPreparedTimeRecordForm employeeNumber workDate =
    Http.get
        { url = URLs.timerecordPreparedFormGetEndpoint employeeNumber workDate
        , expect = Http.expectJson PrepareForm preparedTimeRecordFormDecoder
        }


type alias TimeRecordPostResponse =
    { status : PostedStatus
    , errors : List Message
    , succeeded : RegisteredData
    }


type alias RegisteredData =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    }
