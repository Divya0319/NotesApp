package com.practicesession.notesapp.model

class Note {
    var id: Int? = null
    var title: String? = null
    var content: String? = null
    var fontStyle: Int? = null

    constructor(id: Int, title: String, content: String, fontStyle: Int) {
        this.id = id
        this.title = title
        this.content = content
        this.fontStyle = fontStyle
    }
}