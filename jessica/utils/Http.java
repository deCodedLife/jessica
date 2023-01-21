package jessica.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.Map;

public class Http
{
    private String USER_AGENT;
    private List<Map<String, String>> URL_PARAMS;

    /**
     * Initialize basic values
     */
    public Http()
    {
        USER_AGENT = "Mozilla/5.0";
        URL_PARAMS = new ArrayList<Map<String, String>>();
    }

    public void addUrlParam(Map<String, String> param)
    {
        URL_PARAMS.add(param);
    }

    private String getUriParameters()
    {
        if ( URL_PARAMS.isEmpty() ) return "";

        String params = "?";

        for (Map<String, String> param: URL_PARAMS)
        {
            for (String key: param.keySet())
            {
                params += key + "=" + param.get( key ) + "&";
            }
        }

        params = params.substring(0, params.length() - 1);

        return params;
    }

    /**
     * Implements GET method
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String GetRequest(String url) throws IOException
    {
        URL resourceURL = new URL(url + getUriParameters());
        HttpURLConnection request = (HttpURLConnection) resourceURL.openConnection();

        request.setRequestMethod("GET");
        request.setRequestProperty("User-Agent", this.USER_AGENT);

        URL_PARAMS.clear();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuffer strBuffer = new StringBuffer();

            String line;
            while((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }

            return strBuffer.toString();
        }
        catch ( IOException e )
        {
            return "";
        }
    }
}
