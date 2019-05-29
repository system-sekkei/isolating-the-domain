module Main exposing (main)

import Browser
import Browser.Navigation
import Html exposing (Attribute, Html, a, div, footer, nav, p, span, text)
import Html.Attributes exposing (class, href)
import Html.Events
import Json.Decode
import Pages.Attendance
import Pages.Dashboard
import Pages.NotFound
import Pages.Payroll exposing (Model, PageState(..))
import Pages.TimeRecord
import Route exposing (Route(..))
import Task
import Time
import Types.Employee.EmployeeNumber exposing (EmployeeNumber(..))
import Types.Time.ClientTime exposing (ClientTime(..))
import Types.Time.WorkDate as WorkDate
import Types.Time.YearMonth as YearMonth
import URLs
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
    , navState : NavState
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


type NavState
    = BurgerClosed
    | BurgerOpened


type alias PageName =
    String


init : () -> Url.Url -> Browser.Navigation.Key -> ( Model, Cmd Msg )
init _ _ key =
    ( Model key BurgerClosed InitializingPage (ClientTime Time.utc (Time.millisToPosix 0))
    , Task.perform Initialize <| Task.map2 Tuple.pair Time.here Time.now
    )



-- UPDATE


type Msg
    = Initialize ( Time.Zone, Time.Posix )
    | LinkClicked Browser.UrlRequest
    | UrlChanged Url.Url
    | ClickNavBurger
    | ClickNavItem
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

        ClickNavBurger ->
            case model.navState of
                BurgerClosed ->
                    ( { model | navState = BurgerOpened }, Cmd.none )

                BurgerOpened ->
                    ( { model | navState = BurgerClosed }, Cmd.none )

        ClickNavItem ->
            case model.navState of
                BurgerOpened ->
                    ( { model | navState = BurgerClosed }, Cmd.none )

                _ ->
                    ( model, Cmd.none )

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
                [ text "Now Initializing..."
                    |> appContainer
                ]
            }

        DashboardPage dashboardModel ->
            { title = dashboardModel.pageName
            , body =
                [ appNavbar model
                , Pages.Dashboard.view dashboardModel
                    |> appContainer
                , appFooter
                ]
            }

        PayrollPage payrollModel ->
            { title = payrollModel.pageName
            , body =
                [ appNavbar model
                , Pages.Payroll.view payrollModel
                    |> appContainer
                    |> Html.map PayrollMsg
                , appFooter
                ]
            }

        AttendancePage attendanceModel ->
            { title = attendanceModel.pageName
            , body =
                [ appNavbar model
                , Pages.Attendance.view attendanceModel
                    |> appContainer
                    |> Html.map AttendanceMsg
                , appFooter
                ]
            }

        TimeRecordPage timeRecordModel ->
            { title = timeRecordModel.pageName
            , body =
                [ appNavbar model
                , Pages.TimeRecord.view timeRecordModel
                    |> appContainer
                    |> Html.map TimeRecordMsg
                , appFooter
                ]
            }

        NotFoundPage ->
            Pages.NotFound.view


appContainer : Html msg -> Html msg
appContainer element =
    div [ class "container" ] [ element ]


appNavbar : Model -> Html Msg
appNavbar model =
    nav [ class "navbar is-light" ]
        [ div [ class "navbar-brand" ]
            [ span [ class "navbar-item" ] [ navbarTitle model ]
            , span
                [ navbarBurgerStyle model
                , onClick ClickNavBurger
                ]
                [ span [] [], span [] [], span [] [] ]
            ]
        , div [ navbarMenuStyle model ]
            [ a
                [ dashboardNavItemStyle model
                , href URLs.dashboardPageURL
                , onClick ClickNavItem
                ]
                [ text "ダッシュボード" ]
            , a
                [ timeRecordNavItemStyle model
                , href (URLs.timeRecordPageURL defaultEmployeeNumber (WorkDate.from model.clientTime))
                , onClick ClickNavItem
                ]
                [ text "勤務時間の入力" ]
            , a
                [ payrollNavItemStyle model
                , href (URLs.payrollPageURL (YearMonth.from model.clientTime))
                , onClick ClickNavItem
                ]
                [ text "給与の一覧" ]
            , a
                [ class "navbar-item"
                , href "#"
                , onClick ClickNavItem
                ]
                [ text "従業員の一覧" ]
            ]
        ]


defaultEmployeeNumber : EmployeeNumber
defaultEmployeeNumber =
    EmployeeNumber 1


navbarTitle : Model -> Html msg
navbarTitle model =
    case model.page of
        DashboardPage dashboardModel ->
            text dashboardModel.pageName

        PayrollPage payrollModel ->
            text payrollModel.pageName

        AttendancePage attendanceModel ->
            text attendanceModel.pageName

        TimeRecordPage attendanceModel ->
            text attendanceModel.pageName

        _ ->
            text ""


navbarBurgerStyle : Model -> Attribute msg
navbarBurgerStyle model =
    case model.navState of
        BurgerOpened ->
            class "navbar-burger burger is-active"

        BurgerClosed ->
            class "navbar-burger burger"


navbarMenuStyle : Model -> Attribute msg
navbarMenuStyle model =
    case model.navState of
        BurgerOpened ->
            class "navbar-menu is-active"

        BurgerClosed ->
            class "navbar-menu burger"


dashboardNavItemStyle : Model -> Attribute msg
dashboardNavItemStyle model =
    case model.page of
        DashboardPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"


timeRecordNavItemStyle : Model -> Attribute msg
timeRecordNavItemStyle model =
    case model.page of
        TimeRecordPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"


payrollNavItemStyle : Model -> Attribute msg
payrollNavItemStyle model =
    case model.page of
        PayrollPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"


appFooter : Html msg
appFooter =
    footer [ class "footer" ]
        [ div [ class "content has-text-centered" ] []
        , p []
            [ a [ href "https://github.com/system-sekkei/isolating-the-domain" ] [ text "isolating-the-domain" ] ]
        ]


onClick : msg -> Attribute msg
onClick msg =
    Html.Events.on "click" (Json.Decode.succeed msg)
