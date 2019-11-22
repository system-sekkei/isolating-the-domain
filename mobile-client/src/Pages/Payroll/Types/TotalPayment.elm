module Pages.Payroll.Types.TotalPayment exposing (TotalPayment(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type TotalPayment
    = EmptyTotalPayment
    | FormattedTotalPayment String


decoder : Decoder TotalPayment
decoder =
    string |> andThen (\str -> succeed (FormattedTotalPayment str))


toString : TotalPayment -> String
toString totalPayment =
    case totalPayment of
        FormattedTotalPayment value ->
            value

        EmptyTotalPayment ->
            "-"
