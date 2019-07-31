module Pages.NotFound exposing (view)

import Html exposing (..)
import Html.Attributes exposing (..)



-- VIEW


view : Html msg
view =
    Html.main_ []
        [ section [ class "section" ]
            [ text "該当のページはありません" ]
        ]
