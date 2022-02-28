package ca.jrvs.apps.Twitter.dao.helper;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

public interface HttpHelper {
	/**
	 * Executes a HTTP Post call to the Twitter.
	 *
	 * @param uri with which a Post request to Twitter will be requested.
	 * @return {@link HttpResponse} which contains the response of the request made.
	 * @throws IOException if the HTTP Post connection error.
	 */
	HttpResponse httpPost(URI uri) throws IOException;

	/**
	 * Execute a HTTP Get call to the Twitter.
	 *
	 * @param uri on which a Get request to Twitter will be requested.
	 * @return @link HttpResponse} which contains the response of the request made.
	 * @throws IOException if the HTTP GET connection error.
	 */
	HttpResponse httpGet(URI uri) throws IOException;
}
