module Pages.Payroll exposing (Model, Msg(..), PageState(..), init, update, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (href)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Pages.Payroll.Types.PayrollStatusMessage as PayrollStatusMessage exposing (PayrollStatusMessage(..))
import Pages.Payroll.Types.TotalPayment as TotalPayment exposing (TotalPayment(..))
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName(..))
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber(..))
import Types.Time.YearMonth as YearMonth exposing (YearMonth(..))
import URLs



-- MODEL


type alias Model =
    { yearMonth : YearMonth
    , state : PageState
    }


type PageState
    = Initializing
    | Loaded (List Payroll)


init : YearMonth -> ( Model, Cmd Msg )
init yearMonth =
    ( Model yearMonth Initializing, getPayrolls yearMonth )



-- UPDATE


type Msg
    = GotPayrolls (Result Http.Error (List Payroll))


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotPayrolls result ->
            case result of
                Ok payrolls ->
                    ( { model | state = Loaded payrolls }, Cmd.none )

                Err error ->
                    Debug.todo (Debug.toString error)



-- VIEW


view : Model -> Browser.Document msg
view model =
    { title = "給与の一覧"
    , body =
        [ h1 [] [ text "給与の一覧" ]
        , case model.state of
            Initializing ->
                div []
                    [ text "Now Loading..." ]

            Loaded payrolls ->
                div []
                    [ payrollTableTitle model
                    , payrollTable model payrolls
                    ]
        ]
    }


payrollTableTitle : Model -> Html msg
payrollTableTitle model =
    div []
        [ a
            [ href
                (model.yearMonth
                    |> YearMonth.previous
                    |> URLs.payrollPageURL
                )
            ]
            [ text "前の月" ]
        , h2 [] [ text (model.yearMonth |> YearMonth.toString) ]
        , a
            [ href
                (model.yearMonth
                    |> YearMonth.next
                    |> URLs.payrollPageURL
                )
            ]
            [ text "次の月" ]
        ]


payrollTable : Model -> List Payroll -> Html msg
payrollTable model payrolls =
    table []
        [ thead []
            [ tr []
                [ td [] [ text "従業員番号" ]
                , td [] [ text "氏名" ]
                , td [] [ text "支払額" ]
                , td [] [ text "備考" ]
                , td [] []
                ]
            ]
        , tbody []
            (payrollRows model payrolls)
        ]


payrollRows : Model -> List Payroll -> List (Html msg)
payrollRows model payrolls =
    List.map (payrollRow model) payrolls


payrollRow : Model -> Payroll -> Html msg
payrollRow model payroll =
    tr []
        [ td [] [ text (payroll.employeeNumber |> EmployeeNumber.toString) ]
        , td [] [ text (payroll.employeeName |> EmployeeName.toString) ]
        , td [] [ text (payroll.totalPayment |> TotalPayment.toString) ]
        , td [] [ text (payroll.message |> PayrollStatusMessage.toString) ]
        , td [] [ a [ href (URLs.attendancePageURL payroll.employeeNumber model.yearMonth) ] [ text "勤務時間の一覧へ" ] ]
        ]



-- HTTP


type alias Payroll =
    { employeeNumber : EmployeeNumber
    , employeeName : EmployeeName
    , totalPayment : TotalPayment
    , message : PayrollStatusMessage
    }


getPayrolls : YearMonth -> Cmd Msg
getPayrolls yearMonth =
    Http.get
        { url = URLs.payrollGetEndpoint yearMonth
        , expect = Http.expectJson GotPayrolls payrollsDecoder
        }


payrollsDecoder : Json.Decode.Decoder (List Payroll)
payrollsDecoder =
    Json.Decode.field "list" (Json.Decode.list payrollDecoder)


payrollDecoder : Json.Decode.Decoder Payroll
payrollDecoder =
    Json.Decode.succeed Payroll
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "employeeName" EmployeeName.decoder
        |> Json.Decode.Pipeline.optional "totalPayment" TotalPayment.decoder EmptyTotalPayment
        |> Json.Decode.Pipeline.optional "message" PayrollStatusMessage.decoder EmptyPayrollStatusMessage
