package nodscraper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//this class is for restricting the length of JTextField inputs
@SuppressWarnings("serial")
public final class LengthRestrictedDocument extends PlainDocument{

  private final int limit;

  public LengthRestrictedDocument(int limit){
    this.limit = limit;
  }//end of LengthRestrictedDocument method/constructor, I don't know

  @Override
  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
    if (str == null)
      return;
    if ((getLength() + str.length()) <= limit){
      super.insertString(offs, str, a);
    }//end of if statement
  }//end of insertString method
}//end of LengthRestrictedDocument class