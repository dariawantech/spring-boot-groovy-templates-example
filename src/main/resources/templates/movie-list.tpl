yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        title("Movie List")
        link(rel: "stylesheet", type: "text/css", href: "/css/style.css")
    }
    body {
        h1("Movie List")
        div {
            nobr {
                a(href: "/movies/add", "Add Movie")
                yield ' | '
                a(href: "/", "Back to Index")
            }
        }
        br()
        br()
        div {
            table(border: "1") {
                tr {
                    th("Id")
                    th("Title")
                    th("Year")
                    th("Running Time")
                    th("Tags")
                    th("Edit")
                }
                movies.each { movie ->
                    tr {
                        td {
                            a(href:"/movies/$movie.id", "$movie.id")
                        }
                        td {
                            a(href:"/movies/$movie.id", "$movie.title")
                        }
                        td("$movie.releaseYear")
                        td("$movie.runtimeMinutes")
                        td("$movie.tags")
                        td {
                            a(href:"/movies/$movie.id/edit", "Edit")
                        }
                    }
                }
            }
        }
        br()
        br()
        div {
            nobr {
                span {
                    if (hasPrev) {
                        a(href:"/movies?page=$prev", "Prev")
                        yield '   '
                    }
                }
                span {
                    if (hasNext) {
                        a(href:"/movies?page=$next", "Next")
                    }
                }
            }
        }
    }
}