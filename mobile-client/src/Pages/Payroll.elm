module Pages.Payroll exposing (Model, Msg(..), PageState(..), init, update, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (href)
import Http
import Json.Decode exposing (Decoder, andThen, field, list, string, succeed)
import Json.Decode.Pipeline exposing (optional, required)
import Pages.URLs
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName(..))
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber(..))
import Types.Payroll.PayrollStatusMessage as PayrollStatusMessage exposing (PayrollStatusMessage(..))
import Types.Payroll.TotalPayment as TotalPayment exposing (TotalPayment(..))
import Types.TIme.YearMonth exposing (YearMonth(..))



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
                text "Now Loading..."

            Loaded payrolls ->
                payrollTable model payrolls
        ]
    }


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
        , td [] [ a [ href (Pages.URLs.attendancePageURL payroll.employeeNumber model.yearMonth) ] [ text "勤務時間の一覧へ" ] ]
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
        { url = Pages.URLs.payrollGetEndpoint yearMonth
        , expect = Http.expectJson GotPayrolls payrollsDecoder
        }


payrollsDecoder : Decoder (List Payroll)
payrollsDecoder =
    field "list" (list payrollDecoder)


payrollDecoder : Decoder Payroll
payrollDecoder =
    succeed Payroll
        |> required "employeeNumber" employeeNumberDecoder
        |> required "employeeName" employeeNameDecoder
        |> optional "totalPayment" totalPaymentDecoder EmptyTotalPayment
        |> optional "message" payrollStatusMessageDecoder EmptyPayrollStatusMessage


employeeNumberDecoder : Decoder EmployeeNumber
employeeNumberDecoder =
    string |> andThen (\str -> succeed (EmployeeNumber.parse str))


employeeNameDecoder : Decoder EmployeeName
employeeNameDecoder =
    string |> andThen (\str -> succeed (EmployeeName str))


totalPaymentDecoder : Decoder TotalPayment
totalPaymentDecoder =
    string |> andThen (\str -> succeed (TotalPayment str))


payrollStatusMessageDecoder : Decoder PayrollStatusMessage
payrollStatusMessageDecoder =
    string |> andThen (\str -> succeed (PayrollStatusMessage str))
