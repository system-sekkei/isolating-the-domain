module Pages.TimeRecord exposing (Model, Msg, init, update, view)

import Browser.Navigation
import Components.AppHtmlUtils exposing (onChange)
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Json.Encode
import Pages.TimeRecord.Rules.BreakTimeCapRule as BreakTimeCapRule exposing (BreakTimeCapRule(..))
import Pages.TimeRecord.Rules.WorkTimeRangeRule as WorkingTimeRangeRule exposing (WorkTimeRangeRule(..))
import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute(..))
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour(..))
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute(..))
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute(..))
import Pages.TimeRecord.Types.PostedStatus as PostedStatus exposing (PostedStatus(..))
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour(..))
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute(..))
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName(..))
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber(..))
import Types.Message as Message exposing (Message(..))
import Types.Time.WorkDate as WorkDate exposing (WorkDate(..))
import URLs



-- MODEL


type alias Model =
    -- TODO 登録後のリダイレクトのためにkeyを持ち回るのはちょっといやな感じ。。。親子でメッセージやコマンドのやりとりが複雑になるよりいいか？
    { pageName : PageName
    , key : Browser.Navigation.Key
    , employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , state : PageState
    }


type alias PageName =
    String


type PageState
    = Initializing
    | Editing PreparedTimeRecordForm TimeRecordForm ErrorMessages


type alias ErrorMessages =
    List Message


init : Browser.Navigation.Key -> EmployeeNumber -> WorkDate -> ( Model, Cmd Msg )
init key employeeNumber workDate =
    ( Model "勤務時間の入力" key employeeNumber workDate Initializing, getPreparedTimeRecordForm employeeNumber workDate )



-- UPDATE


type Msg
    = PrepareForm (Result Http.Error PreparedTimeRecordForm)
    | EditForm TimeRecordForm
    | PostForm
    | PostedForm (Result Http.Error TimeRecordPostResponse)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        PrepareForm result ->
            case result of
                Ok prepared ->
                    ( { model | state = Editing prepared prepared.preparedRequest [] }, Cmd.none )

                Err error ->
                    -- TODO サーバエラー時のハンドリング
                    ( model, Cmd.none )

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

                        correlationCheckErrors =
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
                    ( { model | state = Editing prepared validatedForm correlationCheckErrors }, Cmd.none )

                _ ->
                    ( model, Cmd.none )

        PostForm ->
            case model.state of
                Editing _ editing _ ->
                    if isInvalid editing then
                        ( model, Cmd.none )

                    else
                        ( model, postTimeRecordForm editing )

                _ ->
                    ( model, Cmd.none )

        PostedForm result ->
            case result of
                Ok response ->
                    case model.state of
                        Editing prepared postedForm _ ->
                            ( { model | state = Editing prepared postedForm response.errors }
                            , if List.isEmpty response.errors then
                                gotoAttendancePage model postedForm

                              else
                                Cmd.none
                            )

                        _ ->
                            ( model, Cmd.none )

                Err error ->
                    -- TODO サーバエラー時のハンドリング
                    ( model, Cmd.none )


gotoAttendancePage : Model -> TimeRecordForm -> Cmd msg
gotoAttendancePage model postedForm =
    let
        yearMonth =
            WorkDate.toYearMonth postedForm.workDate

        url =
            URLs.attendancePageURL postedForm.employeeNumber yearMonth
    in
    Browser.Navigation.pushUrl model.key url



-- VIEW


view : Model -> Html Msg
view model =
    Html.main_ []
        [ section [ class "section" ]
            [ case model.state of
                Initializing ->
                    div []
                        [ text "Now Loading..." ]

                Editing prepared editing errorMessages ->
                    div []
                        [ errorMessageArea errorMessages
                        , timeRecordForm prepared editing
                        ]
            ]
        ]


errorMessageArea : ErrorMessages -> Html Msg
errorMessageArea errorMessages =
    section []
        (List.map messageLine errorMessages)


messageLine : Message -> Html Msg
messageLine message =
    p [] [ text (message |> Message.toString) ]


timeRecordForm : PreparedTimeRecordForm -> TimeRecordForm -> Html Msg
timeRecordForm prepared editing =
    section []
        [ div [ class "field" ]
            [ label [ class "label" ] [ text "氏名" ]
            , div [ class "control is-expanded" ]
                [ div [ class "select is-fullwidth" ]
                    [ employeeSelectBox prepared editing ]
                ]
            ]
        , div [ class "field" ]
            [ label [ class "label" ] [ text "勤務日" ]
            , div [ class "control" ]
                [ workDateInput editing ]
            , workDateInputErrorMessage editing
            ]
        , div [ class "field is-horizontal" ]
            [ div [ class "field-label" ]
                [ label [ class "label" ] [ text "開始時刻" ] ]
            , div [ class "field-body is-flex" ]
                [ div [ class "field has-addons" ]
                    [ div [ class "control is-expanded" ]
                        [ startHourInput editing ]
                    , div [ class "control" ]
                        [ span [ class "button" ] [ text "時" ] ]
                    ]
                , div [ class "field has-addons" ]
                    [ div [ class "control is-expanded" ]
                        [ startMinuteInput editing ]
                    , div [ class "control" ]
                        [ span [ class "button" ] [ text "分" ] ]
                    ]
                ]
            , startHourInputErrorMessage editing
            , startMinuteInputErrorMessage editing
            ]
        , div [ class "field is-horizontal" ]
            [ div [ class "field-label" ]
                [ label [ class "label" ] [ text "終了時刻" ] ]
            , div [ class "field-body is-flex" ]
                [ div [ class "field has-addons" ]
                    [ div [ class "control is-expanded" ]
                        [ endHourInput editing ]
                    , div [ class "control" ]
                        [ span [ class "button" ] [ text "時" ] ]
                    ]
                , div [ class "field has-addons" ]
                    [ div [ class "control is-expanded" ]
                        [ endMinuteInput editing ]
                    , div [ class "control" ]
                        [ span [ class "button" ] [ text "分" ] ]
                    ]
                ]
            , endHourInputErrorMessage editing
            , endMinuteInputErrorMessage editing
            ]
        , div [ class "field" ]
            [ label [ class "label" ] [ text "休憩時間" ]
            , div [ class "control" ]
                [ daytimeBreakMinuteInput editing
                ]
            , daytimeBreakMinuteInputErrorMessage editing
            ]
        , div [ class "field" ]
            [ label [ class "label" ] [ text "休憩時間（深夜）" ]
            , div [ class "control" ]
                [ midnightBreakMinuteInput editing
                ]
            , midnightBreakMinuteInputErrorMessage editing
            ]
        , div [ class "field" ]
            [ div [ class "control" ]
                [ button
                    [ class "button is-primary is-fullwidth"
                    , onClick PostForm
                    ]
                    [ text "登録" ]
                ]
            ]
        ]


employeeSelectBox : PreparedTimeRecordForm -> TimeRecordForm -> Html Msg
employeeSelectBox prepared editing =
    select
        [ onChange (\str -> EditForm { editing | employeeNumber = EmployeeNumber.parse str }) ]
        (List.map (employeeOption editing) prepared.contractingEmployees)


employeeOption : TimeRecordForm -> Employee -> Html msg
employeeOption editing employee =
    option
        [ value (employee.employeeNumber |> EmployeeNumber.toString)
        , selected (employee.employeeNumber == editing.employeeNumber)
        ]
        [ text (employee.employeeName |> EmployeeName.toString) ]


workDateInput : TimeRecordForm -> Html Msg
workDateInput editing =
    input
        [ type_ "date"
        , class "input"
        , value (editing.workDate |> WorkDate.toString)
        , onInput (\str -> EditForm { editing | workDate = DirtyWorkDate str })
        ]
        []


workDateInputErrorMessage : TimeRecordForm -> Html Msg
workDateInputErrorMessage editing =
    case editing.workDate of
        InvalidWorkDate err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


startHourInput : TimeRecordForm -> Html Msg
startHourInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.startHour |> StartHour.toString)
        , onInput (\str -> EditForm { editing | startHour = DirtyStartHour str })
        ]
        []


startHourInputErrorMessage : TimeRecordForm -> Html Msg
startHourInputErrorMessage editing =
    case editing.startHour of
        InvalidStartHour err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


startMinuteInput : TimeRecordForm -> Html Msg
startMinuteInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.startMinute |> StartMinute.toString)
        , onInput (\str -> EditForm { editing | startMinute = DirtyStartMinute str })
        ]
        []


startMinuteInputErrorMessage : TimeRecordForm -> Html Msg
startMinuteInputErrorMessage editing =
    case editing.startMinute of
        InvalidStartMinute err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


endHourInput : TimeRecordForm -> Html Msg
endHourInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.endHour |> EndHour.toString)
        , onInput (\str -> EditForm { editing | endHour = DirtyEndHour str })
        ]
        []


endHourInputErrorMessage : TimeRecordForm -> Html Msg
endHourInputErrorMessage editing =
    case editing.endHour of
        InvalidEndHour err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


endMinuteInput : TimeRecordForm -> Html Msg
endMinuteInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.endMinute |> EndMinute.toString)
        , onInput (\str -> EditForm { editing | endMinute = DirtyEndMinute str })
        ]
        []


endMinuteInputErrorMessage : TimeRecordForm -> Html Msg
endMinuteInputErrorMessage editing =
    case editing.endMinute of
        InvalidEndMinute err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


daytimeBreakMinuteInput : TimeRecordForm -> Html Msg
daytimeBreakMinuteInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.daytimeBreakTime |> DaytimeBreakMinute.toString)
        , onInput (\str -> EditForm { editing | daytimeBreakTime = DirtyDaytimeBreakMinute str })
        ]
        []


daytimeBreakMinuteInputErrorMessage : TimeRecordForm -> Html Msg
daytimeBreakMinuteInputErrorMessage editing =
    case editing.daytimeBreakTime of
        InvalidDaytimeBreakMinute err _ ->
            text (err |> Message.toString)

        _ ->
            text ""


midnightBreakMinuteInput : TimeRecordForm -> Html Msg
midnightBreakMinuteInput editing =
    input
        [ type_ "number"
        , class "input"
        , value (editing.midnightBreakTime |> MidnightBreakMinute.toString)
        , onInput (\str -> EditForm { editing | midnightBreakTime = DirtyMidnightBreakMinute str })
        ]
        []


midnightBreakMinuteInputErrorMessage : TimeRecordForm -> Html Msg
midnightBreakMinuteInputErrorMessage editing =
    case editing.midnightBreakTime of
        InvalidMidnightBreakMinute err _ ->
            text (err |> Message.toString)

        _ ->
            text ""



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


isInvalid : TimeRecordForm -> Bool
isInvalid form =
    not
        (WorkDate.isValid form.workDate
            && StartHour.isValid form.startHour
            && StartMinute.isValid form.startMinute
            && EndHour.isValid form.endHour
            && EndMinute.isValid form.endMinute
            && DaytimeBreakMinute.isValid form.daytimeBreakTime
            && MidnightBreakMinute.isValid form.midnightBreakTime
        )


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
        { url = URLs.timeRecordPreparedFormGetEndpoint employeeNumber workDate
        , expect = Http.expectJson PrepareForm preparedTimeRecordFormDecoder
        }


encodeTimeRecordForm : TimeRecordForm -> Json.Encode.Value
encodeTimeRecordForm form =
    Json.Encode.object
        [ ( "employeeNumber", EmployeeNumber.encode form.employeeNumber )
        , ( "workDate", WorkDate.encode form.workDate )
        , ( "startHour", StartHour.encode form.startHour )
        , ( "startMinute", StartMinute.encode form.startMinute )
        , ( "endHour", EndHour.encode form.endHour )
        , ( "endMinute", EndMinute.encode form.endMinute )
        , ( "daytimeBreakTime", DaytimeBreakMinute.encode form.daytimeBreakTime )
        , ( "midnightBreakTime", MidnightBreakMinute.encode form.midnightBreakTime )
        ]


type alias TimeRecordPostResponse =
    { status : PostedStatus
    , errors : List Message
    , succeeded : RegisteredData
    }


type alias RegisteredData =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    }


timeRecordPostResponseDecoder : Json.Decode.Decoder TimeRecordPostResponse
timeRecordPostResponseDecoder =
    Json.Decode.succeed TimeRecordPostResponse
        |> Json.Decode.Pipeline.required "status" PostedStatus.decoder
        |> Json.Decode.Pipeline.optional "errors" (Json.Decode.list Message.errorMessagedecoder) []
        |> Json.Decode.Pipeline.optional "succeeded" registeredDataDecoder (RegisteredData EmptyEmployeeNumber EmptyWordDate)


registeredDataDecoder : Json.Decode.Decoder RegisteredData
registeredDataDecoder =
    Json.Decode.succeed RegisteredData
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "workDate" WorkDate.decoder


postTimeRecordForm : TimeRecordForm -> Cmd Msg
postTimeRecordForm form =
    Http.post
        { url = URLs.timeRecordPostEndpoint
        , body = Http.jsonBody (encodeTimeRecordForm form)
        , expect = Http.expectJson PostedForm timeRecordPostResponseDecoder
        }
