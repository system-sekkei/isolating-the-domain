module Main exposing (Model, Msg(..), init, main, subscriptions, update, view)

import Browser
import Browser.Navigation as Nav
import Pages.Attendance
import Pages.Dashboard
import Pages.NotFound
import Pages.Payroll
import Pages.TimeRecord
import Route exposing (Route(..))
import Tuple exposing (first)
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
    { key : Nav.Key
    , page : Page
    }


type Page
    = NotFoundPage
    | DashboardPage
    | PayrollPage
    | AttendancePage
    | TimeRecordPage


init : () -> Url.Url -> Nav.Key -> ( Model, Cmd Msg )
init _ _ key =
    ( Model key DashboardPage, Cmd.none )



-- UPDATE


type Msg
    = LinkClicked Browser.UrlRequest
    | UrlChanged Url.Url


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        LinkClicked urlRequest ->
            case urlRequest of
                Browser.Internal url ->
                    ( model, Nav.pushUrl model.key (Url.toString url) )

                Browser.External href ->
                    ( model, Nav.load href )

        UrlChanged url ->
            ( first (goto url model), Cmd.none )


goto : Url -> Model -> ( Model, Cmd msg )
goto url model =
    case Route.parse url of
        Nothing ->
            ( { model | page = NotFoundPage }, Nav.pushUrl model.key (Url.toString url) )

        Just Top ->
            ( { model | page = DashboardPage }, Nav.pushUrl model.key (Url.toString url) )

        Just DashboardRoute ->
            ( { model | page = DashboardPage }, Nav.pushUrl model.key (Url.toString url) )

        Just (PayrollRoute yearMonth) ->
            ( { model | page = PayrollPage }, Nav.pushUrl model.key (Url.toString url) )

        Just (AttendanceRoute employeeNumber yearMonth) ->
            ( { model | page = AttendancePage }, Nav.pushUrl model.key (Url.toString url) )

        Just (TimeRecordRoute employeeNumber workDate) ->
            ( { model | page = TimeRecordPage }, Nav.pushUrl model.key (Url.toString url) )



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions _ =
    Sub.none



-- VIEW


view : Model -> Browser.Document Msg
view model =
    case model.page of
        DashboardPage ->
            Pages.Dashboard.view

        PayrollPage ->
            Pages.Payroll.view

        AttendancePage ->
            Pages.Attendance.view

        TimeRecordPage ->
            Pages.TimeRecord.view

        NotFoundPage ->
            Pages.NotFound.view
