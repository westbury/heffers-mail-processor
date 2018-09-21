import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import txr.matchers.DocumentMatcher;
import txr.matchers.MatchResults;
import txr.matchers.MatchResultsBase;
import txr.parser.TxrErrorInDocumentException;

public class Main {

	public static void main(String[] args) {
		DocumentMatcher statementMatcher = createMatcherFromResource("heffers.txr");

	    TextTransfer textTransfer = new TextTransfer();

		MatchResults bindings = doMatchingFromClipboard(textTransfer, statementMatcher);

		if (bindings == null || bindings.getCollections(0).isEmpty()) {
			throw new RuntimeException("Data in clipboard does not appear to be copied from an Aqua Card statement transactions page.");
		}

		String order = bindings.getVariable("order").text;
		String yourref = bindings.getVariable("yourref").text;
		String count = bindings.getVariable("count").text;

		StringBuffer buffer = new StringBuffer();
		
		for (MatchResultsBase x : bindings.getCollections(0)) {
			String isbn = x.getVariable("isbn").text;
			String title = x.getVariable("title").text;
			String quantity = x.getVariable("quantity").text;
			String itemprice = x.getVariable("itemprice").text;
			String totalprice = x.getVariable("totalprice").text;
			String title2 = x.getVariable("title2").text;
			if (title2 != null) {
				title += title2;
			}
			
			buffer.append("\"").append(order).append("\",\"")
			.append(yourref).append("\",\"")
			.append(isbn).append("\",\"")
			.append(title).append("\",")
			.append(quantity).append(",")
			.append(itemprice).append(",")
			.append(totalprice).append("\n");
		}
		
	    // put the output back into the clipboard.
	    textTransfer.setClipboardContents(buffer.toString());

	    System.out.println("" + count + " items for order " + order + ", your reference " + yourref + ", have been converted to CSV format and copied back to the clipboard.");
	}

	private static DocumentMatcher createMatcherFromResource(String resourceName) {
		ClassLoader classLoader = Main.class.getClassLoader();
		URL resource = classLoader.getResource(resourceName);
		try (InputStream txrInputStream = resource.openStream()) {
			return new DocumentMatcher(txrInputStream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (TxrErrorInDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static MatchResults doMatchingFromClipboard(TextTransfer textTransfer, DocumentMatcher matcher) {
	    String plainText = textTransfer.getClipboardContents();

		MatchResults bindings = matcher.process(plainText);

		return bindings;
	}


}
