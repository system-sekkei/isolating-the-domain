module Pages.Dashboard exposing (Model, init, view)

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
    = Loaded ClientTime


init : ClientTime -> ( Model, Cmd msg )
init clientTime =
    ( Model (Loaded clientTime), Cmd.none )



-- VIEW


view : Model -> Browser.Document msg
view model =
    { title = "ダッシュボード"
    , body =
        [ h1 [] [ text "ダッシュボード" ]
        , case model.state of
            Loaded clientTime ->
                ul []
                    [ routerLink (YearMonth.from clientTime |> URLs.payrollPageURL) "給与の一覧"
                    , routerLink "/xxxxx" "Not found var1"
                    , routerLink "/32-4098-12/;;asdkl;" "Not found var2"
                    ]
        ]
    }


routerLink : String -> String -> Html msg
routerLink path title =
    li [] [ a [ href path ] [ text title ] ]
