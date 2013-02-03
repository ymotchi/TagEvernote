package code.snippet

import scala.collection.JavaConverters.asScalaBufferConverter
import com.evernote.edam.`type`.NoteSortOrder
import com.evernote.edam.notestore.NoteFilter
import net.liftweb.util.Helpers.strToCssBindPromoter
import scala.xml.XML

object Evernote {

  def note = {
    val authToken = SessionAuthToken.is.get
    val noteStore = SessionNoteStore.is.get

    ".tag *" #>
      (noteStore.listNotebooks(authToken).asScala flatMap {
        notebook =>
          val noteFilter = new NoteFilter();
          noteFilter.setNotebookGuid(notebook.getGuid())
          noteFilter.setOrder(NoteSortOrder.CREATED.getValue())
          noteFilter.setAscending(true)

          noteStore.findNotes(authToken, noteFilter, 0, 100).getNotes().asScala map {
            note => XML.loadString(noteStore.getNoteContent(authToken, note.getGuid()))
          }
      })
  }
}