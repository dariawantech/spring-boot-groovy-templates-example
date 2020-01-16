yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        title("$title")
        link(rel: "stylesheet", type: "text/css", href: "/css/style.css")
    }
    body {
        h1 ("$title")
        a(href: "/movies", "Movie List")
        br()
        br()
        div ("Copyright &copy; dariawan.com")
    }
}