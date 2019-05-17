module Types.Employee.EmployeeNumber exposing (EmployeeNumber(..), parse, toString)


type EmployeeNumber
    = EmptyEmployeeNumber
    | EmployeeNumber Int


parse : String -> EmployeeNumber
parse string =
    String.toInt string
        |> Maybe.map EmployeeNumber
        |> Maybe.withDefault EmptyEmployeeNumber


toString : EmployeeNumber -> String
toString employeeNumber =
    case employeeNumber of
        EmployeeNumber value ->
            String.fromInt value

        EmptyEmployeeNumber ->
            ""
