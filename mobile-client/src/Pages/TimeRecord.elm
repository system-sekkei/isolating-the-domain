module Pages.TimeRecord exposing (Model, Msg, init, update, view)

import Browser.Navigation
import Components.AppHtmlUtils exposing (fieldErrorMessage, httpErrorText, inputStyle, onChange)
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onInput)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Pages.TimeRecord.Rules.BreakTimeCapRule as BreakTimeCapRule
import Pages.TimeRecord.Rules.WorkTimeRangeRule as WorkTimeRangeRule exposing (WorkTimeRangeRule)
import Pages.TimeRecord.TimeRecordForm as TimeRecordForm exposing (TimeRecordForm)
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
    | SystemError Http.Error
    | Editing PreparedTimeRecordForm TimeRecordForm ServerErrorMessages


type alias ServerErrorMessages =
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
                    ( { model | state = SystemError error }, Cmd.none )

        EditForm newEditingForm ->
            case model.state of
                Editing prepared _ serverErrorMessages ->
                    let
                        validatedForm =
                            TimeRecordForm.validate newEditingForm
                    in
                    ( { model | state = Editing prepared validatedForm serverErrorMessages }, Cmd.none )

                _ ->
                    ( model, Cmd.none )

        PostForm ->
            case model.state of
                Editing _ editing _ ->
                    if TimeRecordForm.isInvalid editing then
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
                    ( { model | state = SystemError error }, Cmd.none )


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

                SystemError error ->
                    div []
                        [ h2 [] [ text "System Error" ]
                        , p [] [ httpErrorText error ]
                        ]

                Editing prepared editing errorMessages ->
                    div []
                        [ timeRecordForm prepared editing errorMessages ]
            ]
        ]


timeRecordForm : PreparedTimeRecordForm -> TimeRecordForm -> ServerErrorMessages -> Html Msg
timeRecordForm prepared editing serverErrorMessage =
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
            , workTimeRangeRuleErrorMessage editing
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
            , breakTimeCapRuleErrorMessage editing
            ]
        , errorMessageArea serverErrorMessage
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


errorMessageArea : ServerErrorMessages -> Html Msg
errorMessageArea errorMessages =
    if List.isEmpty errorMessages then
        text ""

    else
        article [ class "message is-danger" ]
            [ div [ class "message-header" ] [ text "Error" ]
            , div [ class "message-body" ] (List.map messageLine errorMessages)
            ]


messageLine : Message -> Html Msg
messageLine message =
    p [ class "help is-danger" ] [ text (message |> Message.toString) ]


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
        , inputStyle (editing.workDate |> WorkDate.isValid)
        , value (editing.workDate |> WorkDate.toString)
        , onInput (\str -> EditForm { editing | workDate = DirtyWorkDate str })
        ]
        []


workDateInputErrorMessage : TimeRecordForm -> Html msg
workDateInputErrorMessage editing =
    editing.workDate
        |> WorkDate.errorMessage
        |> fieldErrorMessage


startHourInput : TimeRecordForm -> Html Msg
startHourInput editing =
    input
        [ type_ "number"
        , inputStyle
            (StartHour.isValid editing.startHour
                && WorkTimeRangeRule.isValid editing.workTimeRangeRule
            )
        , value (editing.startHour |> StartHour.toString)
        , onInput (\str -> EditForm { editing | startHour = DirtyStartHour str })
        ]
        []


startHourInputErrorMessage : TimeRecordForm -> Html msg
startHourInputErrorMessage editing =
    editing.startHour
        |> StartHour.errorMessage
        |> fieldErrorMessage


startMinuteInput : TimeRecordForm -> Html Msg
startMinuteInput editing =
    input
        [ type_ "number"
        , inputStyle
            (StartMinute.isValid editing.startMinute
                && WorkTimeRangeRule.isValid editing.workTimeRangeRule
            )
        , value (editing.startMinute |> StartMinute.toString)
        , onInput (\str -> EditForm { editing | startMinute = DirtyStartMinute str })
        ]
        []


startMinuteInputErrorMessage : TimeRecordForm -> Html msg
startMinuteInputErrorMessage editing =
    editing.startMinute
        |> StartMinute.errorMessage
        |> fieldErrorMessage


endHourInput : TimeRecordForm -> Html Msg
endHourInput editing =
    input
        [ type_ "number"
        , inputStyle
            (EndHour.isValid editing.endHour
                && WorkTimeRangeRule.isValid editing.workTimeRangeRule
            )
        , value (editing.endHour |> EndHour.toString)
        , onInput (\str -> EditForm { editing | endHour = DirtyEndHour str })
        ]
        []


endHourInputErrorMessage : TimeRecordForm -> Html msg
endHourInputErrorMessage editing =
    editing.endHour
        |> EndHour.errorMessage
        |> fieldErrorMessage


endMinuteInput : TimeRecordForm -> Html Msg
endMinuteInput editing =
    input
        [ type_ "number"
        , inputStyle
            (EndMinute.isValid editing.endMinute
                && WorkTimeRangeRule.isValid editing.workTimeRangeRule
            )
        , value (editing.endMinute |> EndMinute.toString)
        , onInput (\str -> EditForm { editing | endMinute = DirtyEndMinute str })
        ]
        []


endMinuteInputErrorMessage : TimeRecordForm -> Html msg
endMinuteInputErrorMessage editing =
    editing.endMinute
        |> EndMinute.errorMessage
        |> fieldErrorMessage


daytimeBreakMinuteInput : TimeRecordForm -> Html Msg
daytimeBreakMinuteInput editing =
    input
        [ type_ "number"
        , inputStyle
            (DaytimeBreakMinute.isValid editing.daytimeBreakTime
                && BreakTimeCapRule.isValid editing.breakTimeCapRule
            )
        , value (editing.daytimeBreakTime |> DaytimeBreakMinute.toString)
        , onInput (\str -> EditForm { editing | daytimeBreakTime = DirtyDaytimeBreakMinute str })
        ]
        []


daytimeBreakMinuteInputErrorMessage : TimeRecordForm -> Html msg
daytimeBreakMinuteInputErrorMessage editing =
    editing.daytimeBreakTime
        |> DaytimeBreakMinute.errorMessage
        |> fieldErrorMessage


midnightBreakMinuteInput : TimeRecordForm -> Html Msg
midnightBreakMinuteInput editing =
    input
        [ type_ "number"
        , inputStyle
            (MidnightBreakMinute.isValid editing.midnightBreakTime
                && BreakTimeCapRule.isValid editing.breakTimeCapRule
            )
        , value (editing.midnightBreakTime |> MidnightBreakMinute.toString)
        , onInput (\str -> EditForm { editing | midnightBreakTime = DirtyMidnightBreakMinute str })
        ]
        []


midnightBreakMinuteInputErrorMessage : TimeRecordForm -> Html msg
midnightBreakMinuteInputErrorMessage editing =
    editing.midnightBreakTime
        |> MidnightBreakMinute.errorMessage
        |> fieldErrorMessage


workTimeRangeRuleErrorMessage : TimeRecordForm -> Html msg
workTimeRangeRuleErrorMessage editing =
    editing.workTimeRangeRule
        |> WorkTimeRangeRule.errorMessage
        |> fieldErrorMessage


breakTimeCapRuleErrorMessage : TimeRecordForm -> Html msg
breakTimeCapRuleErrorMessage editing =
    editing.breakTimeCapRule
        |> BreakTimeCapRule.errorMessage
        |> fieldErrorMessage



-- HTTP


type alias PreparedTimeRecordForm =
    { contractingEmployees : List Employee
    , preparedRequest : TimeRecordForm
    }


type alias Employee =
    { employeeNumber : EmployeeNumber
    , employeeName : EmployeeName
    }


preparedTimeRecordFormDecoder : Json.Decode.Decoder PreparedTimeRecordForm
preparedTimeRecordFormDecoder =
    Json.Decode.succeed PreparedTimeRecordForm
        |> Json.Decode.Pipeline.required "contractingEmployees" (Json.Decode.list employeeDecoder)
        |> Json.Decode.Pipeline.required "preparedRequest" TimeRecordForm.decoder


employeeDecoder : Json.Decode.Decoder Employee
employeeDecoder =
    Json.Decode.succeed Employee
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "employeeName" EmployeeName.decoder


getPreparedTimeRecordForm : EmployeeNumber -> WorkDate -> Cmd Msg
getPreparedTimeRecordForm employeeNumber workDate =
    Http.get
        { url = URLs.timeRecordPreparedFormGetEndpoint employeeNumber workDate
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


timeRecordPostResponseDecoder : Json.Decode.Decoder TimeRecordPostResponse
timeRecordPostResponseDecoder =
    Json.Decode.succeed TimeRecordPostResponse
        |> Json.Decode.Pipeline.required "status" PostedStatus.decoder
        |> Json.Decode.Pipeline.optional "errors" (Json.Decode.list Message.errorMessageDecoder) []
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
        , body = Http.jsonBody (TimeRecordForm.encode form)
        , expect = Http.expectJson PostedForm timeRecordPostResponseDecoder
        }
