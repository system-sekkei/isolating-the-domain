module Pages.Payroll exposing (Model, Msg(..), PageState(..), init, update, view)

import Components.AppHtmlUtils exposing (httpErrorText, nextLine, space)
import Html exposing (..)
import Html.Attributes exposing (..)
import Http exposing (Error(..))
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
    { pageName : PageName
    , yearMonth : YearMonth
    , state : PageState
    }


type alias PageName =
    String


type PageState
    = Initializing
    | SystemError Http.Error
    | Loaded (List Payroll)


init : YearMonth -> ( Model, Cmd Msg )
init yearMonth =
    ( Model "給与の一覧" yearMonth Initializing, getPayrolls yearMonth )



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
                    ( { model | state = SystemError error }, Cmd.none )



-- VIEW


view : Model -> Html msg
view model =
    Html.main_ []
        [ section [ class "section" ]
            [ case model.state of
                Initializing ->
                    div []
                        [ text "Now Loading..." ]

                SystemError error ->
                    div []
                        [ h2 [] [ text "System Error" ]
                        , p [] [ httpErrorText error ]
                        ]

                Loaded payrolls ->
                    div []
                        [ payrollTableTitle model
                        , payrollTable model payrolls
                        ]
            ]
        ]


payrollTableTitle : Model -> Html msg
payrollTableTitle model =
    nav [ class "level is-mobile" ]
        [ span [ class "level-item has-text-centered" ]
            [ a
                [ href
                    (model.yearMonth
                        |> YearMonth.previous
                        |> URLs.payrollPageURL
                    )
                ]
                [ span [ class "mdi mdi-chevron-left" ] []
                , text "前の月"
                ]
            ]
        , span [ class "level-item has-text-centered" ]
            [ h2 [ class "title" ] [ text (model.yearMonth |> YearMonth.toString) ] ]
        , span [ class "level-item has-text-centered" ]
            [ a
                [ href
                    (model.yearMonth
                        |> YearMonth.next
                        |> URLs.payrollPageURL
                    )
                ]
                [ text "次の月"
                , span [ class "mdi mdi-chevron-right" ] []
                ]
            ]
        ]


payrollTable : Model -> List Payroll -> Html msg
payrollTable model payrolls =
    table [ class "table is-striped is-fullwidth" ]
        [ thead []
            [ tr []
                [ td []
                    [ text "従業員番号 / 氏名"
                    , nextLine
                    , indentText "支払額"
                    , nextLine
                    , indentText "備考"
                    ]
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
        [ td []
            [ text
                ((payroll.employeeNumber |> EmployeeNumber.toPrefixnize)
                    ++ (payroll.employeeName |> EmployeeName.toString)
                )
            , nextLine
            , indentText (payroll.totalPayment |> TotalPayment.toString)
            , nextLine
            , indentText (payroll.message |> PayrollStatusMessage.toString)
            ]
        , td [] [ a [ href (URLs.attendancePageURL payroll.employeeNumber model.yearMonth) ] [ text "勤務時間の一覧へ" ] ]
        ]


indentText : String -> Html msg
indentText string =
    text (space ++ space ++ string)



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
