module Pages.Payroll exposing (Model, Msg(..), PageState(..), init, update, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (href)
import Http
import Json.Decode exposing (Decoder, field, int, list, string, succeed)
import Json.Decode.Pipeline exposing (optional, required)



-- MODEL


type alias Model =
    { yearMonth : YearMonth
    , state : PageState
    }


type PageState
    = Initializing
    | Loaded (List Payroll)


type alias YearMonth =
    String


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
        [ td [] [ text payroll.employeeNumber ]
        , td [] [ text payroll.employeeName ]
        , td [] [ text payroll.totalPayment ]
        , td [] [ text payroll.message ]
        , td [] [ a [ href (attendanceUrl payroll.employeeNumber model.yearMonth) ] [] ]
        ]


attendanceUrl : EmployeeNumber -> YearMonth -> String
attendanceUrl employeeNumber yearMonth =
    "/attendance/" ++ employeeNumber ++ "/" ++ yearMonth



-- HTTP


type alias Payroll =
    { employeeNumber : EmployeeNumber
    , employeeName : EmployeeName
    , totalPayment : TotalPayment
    , message : Message
    }


type alias EmployeeNumber =
    String


type alias EmployeeName =
    String


type alias TotalPayment =
    String


type alias Message =
    String


getPayrolls : YearMonth -> Cmd Msg
getPayrolls yearMonth =
    Http.get
        { url = "/api/payroll/" ++ yearMonth
        , expect = Http.expectJson GotPayrolls payrollsDecoder
        }


payrollsDecoder : Decoder (List Payroll)
payrollsDecoder =
    field "list" (list payrollDecoder)


payrollDecoder : Decoder Payroll
payrollDecoder =
    succeed Payroll
        |> required "employeeNumber" string
        |> required "employeeName" string
        |> optional "totalPayment" string ""
        |> optional "message" string ""
