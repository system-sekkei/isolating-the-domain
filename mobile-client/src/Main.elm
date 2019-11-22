module Main exposing (main)

import Browser
import Browser.Navigation
import Components.AppFooter exposing (appFooter)
import Components.AppNavbar as AppNavbar exposing (appNavbar)
import Html exposing (..)
import Html.Attributes exposing (class, href)
import Pages exposing (Page(..))
import Pages.Attendance
import Pages.Dashboard
import Pages.NotFound
import Pages.Payroll exposing (Model, PageState(..))
import Pages.TimeRecord
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
    , navbarState : AppNavbar.State
    , page : Page
    , clientTime : ClientTime
    }


init : () -> Url.Url -> Browser.Navigation.Key -> ( Model, Cmd Msg )
init _ url key =
    ( Model key AppNavbar.BurgerClosed InitializingPage (ClientTime Time.utc (Time.millisToPosix 0))
    , Task.perform (Initialize url) (Task.map2 Tuple.pair Time.here Time.now)
    )



-- UPDATE


type Msg
    = Initialize Url.Url ( Time.Zone, Time.Posix )
    | LinkClicked Browser.UrlRequest
    | UrlChanged Url.Url
    | NavbarMsg AppNavbar.Msg
    | PayrollMsg Pages.Payroll.Msg
    | AttendanceMsg Pages.Attendance.Msg
    | TimeRecordMsg Pages.TimeRecord.Msg


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        Initialize url ( zone, posix ) ->
            let
                initClientTime =
                    ClientTime zone posix

                initializedModel =
                    { model | clientTime = initClientTime }
            in
            goto url initializedModel

        LinkClicked urlRequest ->
            case urlRequest of
                Browser.Internal url ->
                    ( model, Browser.Navigation.pushUrl model.key (Url.toString url) )

                Browser.External href ->
                    ( model, Browser.Navigation.load href )

        UrlChanged url ->
            goto url model

        NavbarMsg navbarMsg ->
            let
                navbarModel =
                    AppNavbar.init model.navbarState model.page model.clientTime

                ( newNavbarModel, navbarCmd ) =
                    AppNavbar.update navbarMsg navbarModel
            in
            ( { model | navbarState = newNavbarModel.state }, Cmd.map NavbarMsg navbarCmd )

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
    case Pages.urlParse url of
        Nothing ->
            ( { model | page = NotFoundPage }, Cmd.none )

        Just Pages.Top ->
            let
                ( dashboardModel, _ ) =
                    Pages.Dashboard.init model.clientTime
            in
            ( { model | page = DashboardPage dashboardModel }, Cmd.none )

        Just Pages.DashboardRoute ->
            let
                ( dashboardModel, _ ) =
                    Pages.Dashboard.init model.clientTime
            in
            ( { model | page = DashboardPage dashboardModel }, Cmd.none )

        Just (Pages.PayrollRoute yearMonth) ->
            let
                ( payrollModel, payrollCmd ) =
                    Pages.Payroll.init yearMonth
            in
            ( { model | page = PayrollPage payrollModel }, Cmd.map PayrollMsg payrollCmd )

        Just (Pages.AttendanceRoute employeeNumber yearMonth) ->
            let
                ( attendanceModel, attendanceCmd ) =
                    Pages.Attendance.init employeeNumber yearMonth
            in
            ( { model | page = AttendancePage attendanceModel }, Cmd.map AttendanceMsg attendanceCmd )

        Just (Pages.TimeRecordRoute employeeNumber workDate) ->
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
                [ text "Now Initializing..."
                    |> appContainer
                ]
            }

        DashboardPage dashboardModel ->
            { title = dashboardModel.pageName
            , body =
                [ appNavbar (AppNavbar.init model.navbarState model.page model.clientTime)
                    |> Html.map NavbarMsg
                , Pages.Dashboard.view dashboardModel
                    |> appContainer
                , appFooter
                ]
            }

        PayrollPage payrollModel ->
            { title = payrollModel.pageName
            , body =
                [ appNavbar (AppNavbar.init model.navbarState model.page model.clientTime)
                    |> Html.map NavbarMsg
                , Pages.Payroll.view payrollModel
                    |> appContainer
                    |> Html.map PayrollMsg
                , appFooter
                ]
            }

        AttendancePage attendanceModel ->
            { title = attendanceModel.pageName
            , body =
                [ appNavbar (AppNavbar.init model.navbarState model.page model.clientTime)
                    |> Html.map NavbarMsg
                , Pages.Attendance.view attendanceModel
                    |> appContainer
                    |> Html.map AttendanceMsg
                , appFooter
                ]
            }

        TimeRecordPage timeRecordModel ->
            { title = timeRecordModel.pageName
            , body =
                [ appNavbar (AppNavbar.init model.navbarState model.page model.clientTime)
                    |> Html.map NavbarMsg
                , Pages.TimeRecord.view timeRecordModel
                    |> appContainer
                    |> Html.map TimeRecordMsg
                , appFooter
                ]
            }

        NotFoundPage ->
            { title = "NOT FOUND"
            , body =
                [ appNavbar (AppNavbar.init model.navbarState model.page model.clientTime)
                    |> Html.map NavbarMsg
                , Pages.NotFound.view
                    |> appContainer
                    |> Html.map TimeRecordMsg
                , appFooter
                ]
            }


appContainer : Html msg -> Html msg
appContainer element =
    div [ class "container" ] [ element ]
