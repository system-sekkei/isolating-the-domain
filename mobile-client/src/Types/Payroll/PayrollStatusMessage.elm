module Types.Payroll.PayrollStatusMessage exposing (PayrollStatusMessage(..), toString)


type PayrollStatusMessage
    = EmptyPayrollStatusMessage
    | PayrollStatusMessage String


toString : PayrollStatusMessage -> String
toString message =
    case message of
        PayrollStatusMessage value ->
            value

        EmptyPayrollStatusMessage ->
            ""
