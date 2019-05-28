module Main exposing (main)

import Browser
import Browser.Navigation
import Html exposing (text)
import Pages.Attendance
import Pages.Dashboard
import Pages.NotFound
import Pages.Payroll exposing (Model, PageState(..))
import Pages.TimeRecord
import Route exposing (Route(..))
import Task
import Time
import Types.Time.ClientTime exposing (ClientTime(..))
import Url exposing (Url)


main =
    Browser.application
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        , onUrlChange = UrlChanged
        , onUrlRequest = LinkClicked
        }



-- MODEL


type alias Model =
    { key : Browser.Navigation.Key
    , page : Page
    , clientTime : ClientTime
    }


type Page
    = NotFoundPage
    | InitializingPage
    | DashboardPage Pages.Dashboard.Model
    | PayrollPage Pages.Payroll.Model
    | AttendancePage Pages.Attendance.Model
    | TimeRecordPage Pages.TimeRecord.Model


init : () -> Url.Url -> Browser.Navigation.Key -> ( Model, Cmd Msg )
init _ _ key =
    ( Model key InitializingPage (ClientTime Time.utc (Time.millisToPosix 0))
    , Task.perform Initialize <| Task.map2 Tuple.pair Time.here Time.now
    )



-- UPDATE


type Msg
    = Initialize ( Time.Zone, Time.Posix )
    | LinkClicked Browser.UrlRequest
    | UrlChanged Url.Url
    | PayrollMsg Pages.Payroll.Msg
    | AttendanceMsg Pages.Attendance.Msg
    | TimeRecordMsg Pages.TimeRecord.Msg


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        Initialize ( zone, posix ) ->
            let
                initClientTime =
                    ClientTime zone posix

                ( dashboardModel, _ ) =
                    Pages.Dashboard.init initClientTime
            in
            ( { model | clientTime = initClientTime, page = DashboardPage dashboardModel }, Cmd.none )

        LinkClicked urlRequest ->
            case urlRequest of
                Browser.Internal url ->
                    ( model, Browser.Navigation.pushUrl model.key (Url.toString url) )

                Browser.External href ->
                    ( model, Browser.Navigation.load href )

        UrlChanged url ->
            goto url model

        PayrollMsg payrollMsg ->
            case model.page of
                PayrollPage payrollModel ->
                    let
                        ( newPayrollModel, payrollCmd ) =
                            Pages.Payroll.update payrollMsg payrollModel
                    in
                    ( { model | page = PayrollPage newPayrollModel }, Cmd.map PayrollMsg payrollCmd )

                _ ->
                    ( model, Cmd.none )

        AttendanceMsg attendanceMsg ->
            case model.page of
                AttendancePage attendanceModel ->
                    let
                        ( newAttendanceModel, attendanceCmd ) =
                            Pages.Attendance.update attendanceMsg attendanceModel
                    in
                    ( { model | page = AttendancePage newAttendanceModel }, Cmd.map AttendanceMsg attendanceCmd )

                _ ->
                    ( model, Cmd.none )

        TimeRecordMsg timeRecordMsg ->
            case model.page of
                TimeRecordPage timeRecordModel ->
                    let
                        ( newTimeRecordModel, timeRecordCmd ) =
                            Pages.TimeRecord.update timeRecordMsg timeRecordModel
                    in
                    ( { model | page = TimeRecordPage newTimeRecordModel }, Cmd.map TimeRecordMsg timeRecordCmd )

                _ ->
                    ( model, Cmd.none )


goto : Url -> Model -> ( Model, Cmd Msg )
goto url model =
    case Route.parse url of
        Nothing ->
            ( { model | page = NotFoundPage }, Cmd.none )

        Just Top ->
            let
                ( dashboardModel, _ ) =
                    Pages.Dashboard.init model.clientTime
            in
            ( { model | page = DashboardPage dashboardModel }, Cmd.none )

        Just DashboardRoute ->
            let
                ( dashboardModel, _ ) =
                    Pages.Dashboard.init model.clientTime
            in
            ( { model | page = DashboardPage dashboardModel }, Cmd.none )

        Just (PayrollRoute yearMonth) ->
            let
                ( payrollModel, payrollCmd ) =
                    Pages.Payroll.init yearMonth
            in
            ( { model | page = PayrollPage payrollModel }, Cmd.map PayrollMsg payrollCmd )

        Just (AttendanceRoute employeeNumber yearMonth) ->
            let
                ( attendanceModel, attendanceCmd ) =
                    Pages.Attendance.init employeeNumber yearMonth
            in
            ( { model | page = AttendancePage attendanceModel }, Cmd.map AttendanceMsg attendanceCmd )

        Just (TimeRecordRoute employeeNumber workDate) ->
            let
                ( timeRecordModel, timeRecordCmd ) =
                    Pages.TimeRecord.init model.key employeeNumber workDate
            in
            ( { model | page = TimeRecordPage timeRecordModel }, Cmd.map TimeRecordMsg timeRecordCmd )



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions _ =
    Sub.none



-- VIEW


view : Model -> Browser.Document Msg
view model =
    case model.page of
        InitializingPage ->
            { title = "Initializing"
            , body =
                [ text "Now Initializing..." ]
            }

        DashboardPage dashboardModel ->
            { title = "ダッシュボード"
            , body =
                [ Pages.Dashboard.view dashboardModel ]
            }

        PayrollPage payrollModel ->
            { title = "給与の一覧"
            , body =
                [ Pages.Payroll.view payrollModel
                    |> Html.map PayrollMsg
                ]
            }

        AttendancePage attendanceModel ->
            { title = "勤務時間の一覧"
            , body =
                [ Pages.Attendance.view attendanceModel
                    |> Html.map AttendanceMsg
                ]
            }

        TimeRecordPage timeRecordModel ->
            { title = "勤務時間の入力"
            , body =
                [ Pages.TimeRecord.view timeRecordModel
                    |> Html.map TimeRecordMsg
                ]
            }

        NotFoundPage ->
            Pages.NotFound.view
