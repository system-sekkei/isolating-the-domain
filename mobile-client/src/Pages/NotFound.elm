module Pages.NotFound exposing (view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)



-- VIEW


view : Browser.Document msg
view =
    { title = "該当のページはありません"
    , body =
        [ h1 [] [ text "NOT FOUND" ]
        , text "該当のページはありません"
        ]
    }
