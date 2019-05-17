module Types.Employee.EmployeeName exposing (EmployeeName(..), toString)


type EmployeeName
    = EmptyEmployeeName
    | EmployeeName String


toString : EmployeeName -> String
toString employeeName =
    case employeeName of
        EmployeeName value ->
            value

        EmptyEmployeeName ->
            ""
