module Main exposing (Model, Msg(..), init, main, subscriptions, update, view)

import Browser
import Browser.Navigation as Nav
import Pages.Attendance
import Pages.Dashboard
import Pages.NotFound
import Pages.Payroll exposing (Model, PageState(..))
import Pages.TimeRecord
import Route exposing (Route(..))
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
    | PayrollPage Pages.Payroll.Model
    | AttendancePage Pages.Attendance.Model
    | TimeRecordPage Pages.TimeRecord.Model


init : () -> Url.Url -> Nav.Key -> ( Model, Cmd Msg )
init _ _ key =
    ( Model key DashboardPage, Cmd.none )



-- UPDATE


type Msg
    = LinkClicked Browser.UrlRequest
    | UrlChanged Url.Url
    | PayrollMsg Pages.Payroll.Msg


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


goto : Url -> Model -> ( Model, Cmd Msg )
goto url model =
    case Route.parse url of
        Nothing ->
            ( { model | page = NotFoundPage }, Cmd.none )

        Just Top ->
            ( { model | page = DashboardPage }, Cmd.none )

        Just DashboardRoute ->
            ( { model | page = DashboardPage }, Cmd.none )

        Just (PayrollRoute yearMonth) ->
            let
                ( payrollModel, payrollCmd ) =
                    Pages.Payroll.init yearMonth
            in
            ( { model | page = PayrollPage payrollModel }, Cmd.map PayrollMsg payrollCmd )

        Just (AttendanceRoute employeeNumber yearMonth) ->
            ( { model | page = AttendancePage Pages.Attendance.Model }, Cmd.none )

        Just (TimeRecordRoute employeeNumber workDate) ->
            ( { model | page = TimeRecordPage Pages.TimeRecord.Model }, Cmd.none )



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

        PayrollPage payrollModel ->
            Pages.Payroll.view payrollModel

        AttendancePage attendanceModel ->
            Pages.Attendance.view attendanceModel

        TimeRecordPage timeRecordModel ->
            Pages.TimeRecord.view timeRecordModel

        NotFoundPage ->
            Pages.NotFound.view
