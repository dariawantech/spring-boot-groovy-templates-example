yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')        
        if (add) {
            title("Create a Movie")
        }
        else {
            title("Edit Movie")
        }
        link(rel: "stylesheet", type: "text/css", href: "/css/style.css")
    }
    body {
        if (add) {
            h1("Create a Movie:")
        }
        else {
            h1("Edit Movie")
        }
        a(href: "/movies", "Back to Movie List")
        br()
        br()
        form (id:"editForm", action:"$actionUrl", method:"POST") {
            table(border: "0") {
                if (!add) {
                    tr {
                        td("Id")
                        td(":")
                        td(movie.id ?: '')
                    }
                }
                tr {
                    td("Title")
                    td(":")
                    td {
                        input(name: 'title', type: 'text', value: movie.title ?: '')
                    }
                }
                tr {
                    td("Release Year")
                    td(":")
                    td {
                        input(name: 'releaseYear', type: 'number', value: movie.releaseYear ?: '')
                    }
                }
                tr {
                    td("Runtime (Minutes)")
                    td(":")
                    td {
                        input(name: 'runtimeMinutes', type: 'number', value: movie.runtimeMinutes ?: '')
                    }
                }
                tr {
                    td("Tags")
                    td(":")
                    td {
                        input(name: 'tags', type: 'text', value: movie.tags ?: '')
                    }
                }
                tr {
                    td("Language")
                    td(":")
                    td {
                        input(name: 'lang', type: 'text', value: movie.lang ?: '')
                    }
                }
                tr {
                    td("Country")
                    td(":")
                    td {
                        input(name: 'country', type: 'text', value: movie.country ?: '')
                    }
                }
            }
            br()
            if (add) {
                input(type: 'submit', value: 'Create')
            }
            else {
                input(type: 'submit', value: 'Update')
            }
        }
        
        br()
        if (errorMessage!=null) {
            div(class: "error", "$errorMessage")
        }
    }
}  