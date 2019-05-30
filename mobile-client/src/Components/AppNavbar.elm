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
    case model.currentPage of
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
