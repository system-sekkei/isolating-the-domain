module Pages.Attendance exposing (Model, Msg, init, update, view)

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
    { employeeNumber : EmployeeNumber
    , yearMonth : YearMonth
    , state : PageState
    }


type PageState
    = Initializing
    | Loaded Attendance


init : EmployeeNumber -> YearMonth -> ( Model, Cmd Msg )
init employeeNumber yearMonth =
    ( Model employeeNumber yearMonth Initializing, getAttendance employeeNumber yearMonth )



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
    div []
        [ h1 [] [ text "勤務時間の一覧" ]
        , case model.state of
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


attendanceSummary : Attendance -> Html msg
attendanceSummary attendance =
    dl []
        [ dt [] [ text "氏名" ]
        , dd [] [ text (attendance.employeeName |> EmployeeName.toString) ]
        , dt [] [ text "総勤務時間" ]
        , dd [] [ text (attendance.totalWorkTime |> TotalWorkTime.toString) ]
        ]


attendanceTableTitle : Model -> Html msg
attendanceTableTitle model =
    div []
        [ a
            [ href
                (model.yearMonth
                    |> YearMonth.previous
                    |> URLs.attendancePageURL model.employeeNumber
                )
            ]
            [ text "前の月" ]
        , h2 [] [ text (model.yearMonth |> YearMonth.toString) ]
        , a
            [ href
                (model.yearMonth
                    |> YearMonth.next
                    |> URLs.attendancePageURL model.employeeNumber
                )
            ]
            [ text "次の月" ]
        ]


attendanceTable : Model -> Attendance -> Html msg
attendanceTable model attendance =
    table []
        [ thead []
            [ tr []
                [ td [ colspan 2 ] [ text "日付" ]
                , td [] [ text "開始時刻" ]
                , td [] [ text "終了時刻" ]
                , td [] [ text "休憩時間" ]
                , td [] [ text "勤務時間" ]
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
    tr []
        [ td [] [ text (timeRecord.workDate |> WorkDate.toDayOfMonth) ]
        , td [] [ text (timeRecord.workDate |> WorkDate.toDayOfWeek) ]
        , td [] [ text (timeRecord.startTimePoint |> StartTimePoint.toString) ]
        , td [] [ text (timeRecord.endTimePoint |> EndTimePoint.toString) ]
        , td [] [ text (timeRecord.breakTime |> BreakTime.toString) ]
        , td [] [ text (timeRecord.workTime |> WorkTime.toString) ]
        , td [] [ a [ href (URLs.timerecordPageURL model.employeeNumber timeRecord.workDate) ] [ text "勤務時間編集へ" ] ]
        ]



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
