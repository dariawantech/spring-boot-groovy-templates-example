yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        title("View Movie")
        link(rel: "stylesheet", type: "text/css", href: "/css/style.css")
    }
    body {
        h1("View Movie")
        a(href: "/movies", "Back to Movie List")
        br()
        br()
        div {
            table(border: "0") {
                tr {
                    td("Id")
                    td(":")
                    td(movie.id ?: '')
                }
                tr {
                    td("Title")
                    td(":")
                    td(movie.title ?: '')
                }
                tr {
                    td("Release Year")
                    td(":")
                    td(movie.releaseYear ?: '')
                }
                tr {
                    td("Runtime (Minutes)")
                    td(":")
                    td(movie.runtimeMinutes ?: '')
                }
                tr {
                    td("Tags")
                    td(":")
                    td(movie.tags ?: '')
                }
                tr {
                    td("Language")
                    td(":")
                    td(movie.lang ?: '')
                }
                tr {
                    td("Country")
                    td(":")
                    td(movie.country ?: '')
                }
            }
        }
        br()
        br()
        if (allowDelete) {
            form (id:"deleteForm", action:"/movies/$movie.id/delete", method:"POST") {
                yield 'Delete this movie? '
                input(type: 'submit', value: 'Yes')
            }
        }
        else {
            div {
                a(href: "/movies/$movie.id/edit", "Edit")
                yield ' | '
                a(href: "/movies/$movie.id/delete", "Delete")
            }
        }
        if (errorMessage!=null) {
            div(class: "error", "$errorMessage")
        }
    }
}  