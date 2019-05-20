module Pages.Dashboard exposing (view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Types.Time.ClientTime exposing (ClientTime)
import Types.Time.YearMonth as YearMonth
import URLs



-- MODEL


type alias Model =
    { state : PageState }


type PageState
    = Initializing
    | Loaded ClientTime



-- VIEW


view : ClientTime -> Browser.Document msg
view clientTime =
    { title = "ダッシュボード"
    , body =
        [ text "ダッシュボード"
        , ul []
            [ routerLink (YearMonth.from clientTime |> URLs.payrollPageURL) "給与の一覧"
            , routerLink "/notfound" "Not found"
            ]
        ]
    }


routerLink : String -> String -> Html msg
routerLink path title =
    li [] [ a [ href path ] [ text title ] ]
