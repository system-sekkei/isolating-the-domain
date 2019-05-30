module Pages.Dashboard exposing (Model, init, view)

import Html exposing (..)
import Html.Attributes exposing (..)
import Types.Employee.EmployeeNumber exposing (EmployeeNumber(..))
import Types.Time.ClientTime exposing (ClientTime)
import Types.Time.WorkDate as WorkDate
import Types.Time.YearMonth as YearMonth
import URLs



-- MODEL


type alias Model =
    { pageName : PageName
    , state : PageState
    }


type alias PageName =
    String


type PageState
    = Loaded ClientTime


init : ClientTime -> ( Model, Cmd msg )
init clientTime =
    ( Model "ダッシュボード" (Loaded clientTime), Cmd.none )



-- VIEW


view : Model -> Html msg
view model =
    Html.main_ []
        [ section [ class "section" ]
            [ case model.state of
                Loaded clientTime ->
                    div [ class "tile is-ancestor" ]
                        [ pageLink "mdi mdi-48px mdi-clock-outline" "勤務時間の入力" (URLs.timeRecordPageURL (EmployeeNumber 1) (WorkDate.from clientTime))
                        , pageLink "mdi mdi-48px mdi-currency-jpy" "給与の一覧" (URLs.payrollPageURL (YearMonth.from clientTime))
                        , pageLink "mdi mdi-48px mdi-account" "従業員の一覧" ""
                        ]
            ]
        ]


pageLink : IconClassName -> LinkText -> Link -> Html msg
pageLink iconClassName linkText link =
    a [ href link ]
        [ div [ class "tile is-parent" ]
            [ div [ class "tile is-child box has-text-centered" ]
                [ span [ class "icon is-large" ] [ span [ class iconClassName ] [] ]
                , p [ class "subtitle" ] [ text linkText ]
                ]
            ]
        ]


type alias IconClassName =
    String


type alias LinkText =
    String


type alias Link =
    String
