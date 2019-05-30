module Pages.Attendance exposing (Model, Msg, init, update, view)

import Components.AppHtmlUtils exposing (nextLine, space)
import Html exposing (..)
import Html.Attributes exposing (..)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Pages.Attendance.Types.BreakTime as BreakTime exposing (BreakTime(..))
import Pages.Attendance.Types.EndTimePoint as EndTimePoint exposing (EndTimePoint(..))
import Pages.Attendance.Types.StartTimePoint as StartTimePoint exposing (StartTimePoint(..))
import Pages.Attendance.Types.TotalWorkTime as TotalWorkTime exposing (TotalWorkTime(..))
import Pages.Attendance.Types.WorkTime as WorkTime exposing (WorkTime(..))
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName(..))
import Types.Employee.EmployeeNumber exposing (EmployeeNumber(..))
import Types.Time.WorkDate as WorkDate exposing (WorkDate(..))
import Types.Time.YearMonth as YearMonth exposing (YearMonth(..))
import URLs



-- MODEL


type alias Model =
    { pageName : PageName
    , employeeNumber : EmployeeNumber
    , yearMonth : YearMonth
    , state : PageState
    }


type alias PageName =
    String


type PageState
    = Initializing
    | Loaded Attendance


init : EmployeeNumber -> YearMonth -> ( Model, Cmd Msg )
init employeeNumber yearMonth =
    ( Model "勤務時間の一覧" employeeNumber yearMonth Initializing, getAttendance employeeNumber yearMonth )



-- UPDATE


type Msg
    = GetAttendance (Result Http.Error Attendance)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GetAttendance result ->
            case result of
                Ok attendance ->
                    ( { model | state = Loaded attendance }, Cmd.none )

                Err error ->
                    ( model, Cmd.none )



-- VIEW


view : Model -> Html msg
view model =
    Html.main_ []
        [ section [ class "section" ]
            [ case model.state of
                Initializing ->
                    div []
                        [ text "Now Loading..." ]

                Loaded attendance ->
                    div []
                        [ attendanceSummary attendance
                        , attendanceTableTitle model
                        , attendanceTable model attendance
                        ]
            ]
        ]


attendanceSummary : Attendance -> Html msg
attendanceSummary attendance =
    div [ class "level is-mobile" ]
        [ div [ class "level-item has-text-centered" ]
            [ div []
                [ p [ class "heading" ] [ text "氏名" ]
                , p [ class "title" ] [ text (attendance.employeeName |> EmployeeName.toString) ]
                ]
            ]
        , div [ class "level-item has-text-centered" ]
            [ div []
                [ p [ class "heading" ] [ text "総勤務時間" ]
                , p [ class "title" ] [ text (attendance.totalWorkTime |> TotalWorkTime.toString) ]
                ]
            ]
        ]


attendanceTableTitle : Model -> Html msg
attendanceTableTitle model =
    nav [ class "level is-mobile" ]
        [ span [ class "level-item has-text-centered" ]
            [ a
                [ href
                    (model.yearMonth
                        |> YearMonth.previous
                        |> URLs.attendancePageURL model.employeeNumber
                    )
                ]
                [ span [ class "mdi mdi-chevron-left" ] []
                , text "前の月"
                ]
            ]
        , span [ class "level-item has-text-centered" ]
            [ h2 [ class "title" ] [ text (model.yearMonth |> YearMonth.toString) ] ]
        , span [ class "level-item has-text-centered" ]
            [ a
                [ href
                    (model.yearMonth
                        |> YearMonth.next
                        |> URLs.attendancePageURL model.employeeNumber
                    )
                ]
                [ text "次の月"
                , span [ class "mdi mdi-chevron-right" ] []
                ]
            ]
        ]


attendanceTable : Model -> Attendance -> Html msg
attendanceTable model attendance =
    table [ class "table is-fullwidth" ]
        [ thead []
            [ tr []
                [ td [] [ text "日付" ]
                , td []
                    [ text "開始 - 終了時刻"
                    , nextLine
                    , indentText "勤務時間"
                    , nextLine
                    , indentText "(休憩時間)"
                    ]
                , td [] [ text "編集" ]
                ]
            ]
        , tbody []
            (timerecordRows model attendance)
        ]


timerecordRows : Model -> Attendance -> List (Html msg)
timerecordRows model attendance =
    List.map (timerecordRow model) attendance.list


timerecordRow : Model -> TimeRecord -> Html msg
timerecordRow model timeRecord =
    tr [ rowStyle timeRecord ]
        [ td [] [ text (timeRecord.workDate |> WorkDate.toDayText) ]
        , td [] (timeRecordContent timeRecord)
        , td []
            [ a
                [ class "button"
                , href (URLs.timeRecordPageURL model.employeeNumber timeRecord.workDate)
                ]
                [ editIcon ]
            ]
        ]


timeRecordContent : TimeRecord -> List (Html msg)
timeRecordContent timeRecord =
    if timeRecord.startTimePoint |> StartTimePoint.isEmpty then
        [ text "" ]

    else
        [ text
            ((timeRecord.startTimePoint |> StartTimePoint.toString)
                ++ " - "
                ++ (timeRecord.endTimePoint |> EndTimePoint.toString)
            )
        , nextLine
        , indentText (timeRecord.workTime |> WorkTime.toString)
        , nextLine
        , indentText
            ("(" ++ (timeRecord.breakTime |> BreakTime.toString) ++ ")")
        ]


indentText : String -> Html msg
indentText string =
    text (space ++ space ++ string)


editIcon : Html msg
editIcon =
    span [ class "mdi mdi-pencil" ] []


rowStyle : TimeRecord -> Attribute msg
rowStyle timeRecord =
    if timeRecord.workDate |> WorkDate.isSaturday then
        style "background-color" "rgb(255, 246, 246)"

    else if timeRecord.workDate |> WorkDate.isSunday then
        style "background-color" "rgb(248, 255, 255)"

    else
        class ""



-- HTTP


type alias Attendance =
    { employeeName : EmployeeName
    , totalWorkTime : TotalWorkTime
    , list : List TimeRecord
    }


type alias TimeRecord =
    { workDate : WorkDate
    , startTimePoint : StartTimePoint
    , endTimePoint : EndTimePoint
    , breakTime : BreakTime
    , workTime : WorkTime
    }


getAttendance : EmployeeNumber -> YearMonth -> Cmd Msg
getAttendance employeeNumber yearMonth =
    Http.get
        { url = URLs.attendanceGetEndpoint employeeNumber yearMonth
        , expect = Http.expectJson GetAttendance attendanceDecoder
        }


attendanceDecoder : Json.Decode.Decoder Attendance
attendanceDecoder =
    Json.Decode.succeed Attendance
        |> Json.Decode.Pipeline.required "employeeName" EmployeeName.decoder
        |> Json.Decode.Pipeline.required "totalWorkTime" TotalWorkTime.decoder
        |> Json.Decode.Pipeline.required "list" (Json.Decode.list timeRecordDecoder)


timeRecordDecoder : Json.Decode.Decoder TimeRecord
timeRecordDecoder =
    Json.Decode.succeed TimeRecord
        |> Json.Decode.Pipeline.required "workDate" WorkDate.decoder
        |> Json.Decode.Pipeline.optional "startTimePoint" StartTimePoint.decoder EmptyStartTimePoint
        |> Json.Decode.Pipeline.optional "endTimePoint" EndTimePoint.decoder EmptyEndTimePoint
        |> Json.Decode.Pipeline.optional "breakTime" BreakTime.decoder EmptyBreakTime
        |> Json.Decode.Pipeline.optional "workTime" WorkTime.decoder EmptyWorkTime
