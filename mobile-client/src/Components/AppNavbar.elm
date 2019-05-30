module Components.AppNavbar exposing (Model, Msg, State(..), appNavbar, init, update)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick)
import Pages exposing (Page(..))
import Types.Employee.EmployeeNumber exposing (EmployeeNumber(..))
import Types.Time.ClientTime exposing (ClientTime)
import Types.Time.WorkDate as WorkDate
import Types.Time.YearMonth as YearMonth
import URLs



-- MODEL


type alias Model =
    { state : State
    , currentPage : Page
    , clientTime : ClientTime
    }


type State
    = BurgerClosed
    | BurgerOpened


init : State -> Page -> ClientTime -> Model
init state currentPage clientTime =
    Model state currentPage clientTime



-- UPDATE


type Msg
    = ClickNavBurger
    | ClickNavItem


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        ClickNavBurger ->
            case model.state of
                BurgerClosed ->
                    ( { model | state = BurgerOpened }, Cmd.none )

                BurgerOpened ->
                    ( { model | state = BurgerClosed }, Cmd.none )

        ClickNavItem ->
            case model.state of
                BurgerOpened ->
                    ( { model | state = BurgerClosed }, Cmd.none )

                _ ->
                    ( model, Cmd.none )



-- VIEW


appNavbar : Model -> Html Msg
appNavbar model =
    nav [ class "navbar is-light" ]
        [ div [ class "navbar-brand" ]
            [ span [ class "navbar-item" ] (navbarTitle model)
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
                [ dashboardIcon, text "ダッシュボード" ]
            , a
                [ timeRecordNavItemStyle model
                , href (URLs.timeRecordPageURL defaultEmployeeNumber (WorkDate.from model.clientTime))
                , onClick ClickNavItem
                ]
                [ timeRecordIcon, text "勤務時間の入力" ]
            , a
                [ payrollNavItemStyle model
                , href (URLs.payrollPageURL (YearMonth.from model.clientTime))
                , onClick ClickNavItem
                ]
                [ payrollIcon, text "給与の一覧" ]
            , a
                [ class "navbar-item"
                , href ""
                , onClick ClickNavItem
                ]
                [ employeeIcon, text "従業員の一覧" ]
            ]
        ]


defaultEmployeeNumber : EmployeeNumber
defaultEmployeeNumber =
    EmployeeNumber 1


navbarTitle : Model -> List (Html msg)
navbarTitle model =
    case model.currentPage of
        DashboardPage dashboardModel ->
            [ dashboardIcon, text dashboardModel.pageName ]

        PayrollPage payrollModel ->
            [ payrollIcon, text payrollModel.pageName ]

        AttendancePage attendanceModel ->
            [ timeRecordIcon, text attendanceModel.pageName ]

        TimeRecordPage timeRecordModel ->
            [ timeRecordIcon, text timeRecordModel.pageName ]

        NotFoundPage ->
            [ span [ class "mdi mdi-alert-circle-outline" ] [], text "NOT FOUND" ]

        _ ->
            [ text "" ]


dashboardIcon : Html msg
dashboardIcon =
    span [ class "mdi mdi-speedometer" ] []


payrollIcon : Html msg
payrollIcon =
    span [ class "mdi mdi-currency-jpy" ] []


timeRecordIcon : Html msg
timeRecordIcon =
    span [ class "mdi mdi-clock-outline" ] []


employeeIcon : Html msg
employeeIcon =
    span [ class "mdi mdi-account" ] []


navbarBurgerStyle : Model -> Attribute msg
navbarBurgerStyle model =
    case model.state of
        BurgerOpened ->
            class "navbar-burger burger is-active"

        BurgerClosed ->
            class "navbar-burger burger"


navbarMenuStyle : Model -> Attribute msg
navbarMenuStyle model =
    case model.state of
        BurgerOpened ->
            class "navbar-menu is-active"

        BurgerClosed ->
            class "navbar-menu burger"


dashboardNavItemStyle : Model -> Attribute msg
dashboardNavItemStyle model =
    case model.currentPage of
        DashboardPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"


timeRecordNavItemStyle : Model -> Attribute msg
timeRecordNavItemStyle model =
    case model.currentPage of
        TimeRecordPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"


payrollNavItemStyle : Model -> Attribute msg
payrollNavItemStyle model =
    case model.currentPage of
        PayrollPage _ ->
            class "navbar-item is-active"

        _ ->
            class "navbar-item"
