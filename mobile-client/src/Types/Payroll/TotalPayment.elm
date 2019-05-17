module Types.Payroll.TotalPayment exposing (TotalPayment(..), toString)


type TotalPayment
    = EmptyTotalPayment
    | TotalPayment String


toString : TotalPayment -> String
toString totalPayment =
    case totalPayment of
        TotalPayment value ->
            value

        EmptyTotalPayment ->
            ""
