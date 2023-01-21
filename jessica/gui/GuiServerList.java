package jessica.gui;

import com.google.gson.*;
import jessica.gui.structures.ServerData;
import jessica.utils.Http;
import net.minecraft.client.gui.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Используется наследование от класса GuiMultiplayer
 * Таким образом мы берем весь функционал и можем добавить свой
 * Редактировать этот файл гораздо проще, чем класс GuiMultiplayer целиком, согласитесь)
 */
public class GuiServerList extends GuiMultiplayer
{
    private ServerData selectedServer;
    private Http httpClient;

    public GuiServerList(GuiScreen parentScreen)
    {
        super(parentScreen);

        this.selectedServer = new ServerData();
        this.httpClient = new Http();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        // Apply default actions
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // Get active server
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.serverListSelector.getSelected() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
        if (guilistextended$iguilistentry == null) return;

        // Provide server IP
        this.selectedServer.ip = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData().serverIP;

        /**
         * Creating request to
         * https://funkemunky.cc/vpn?ip=IP&license=true&cache=true
         * where IP is selected server IP
         */

        this.httpClient.addUrlParam(Map.of("ip", this.selectedServer.ip.split(":")[0]));
        this.httpClient.addUrlParam(Map.of("license", "true"));
        this.httpClient.addUrlParam(Map.of("cache", "true"));

        // Sending a request
        String response = this.httpClient.GetRequest( "https://funkemunky.cc/vpn" );
        if ( response.isEmpty() ) return;

        // Parse Json data
        Gson gson = new Gson();
        this.selectedServer = gson.fromJson(response, ServerData.class);
        this.selectedServer.name = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData().serverName;

        // Refresh screen
        mc.updateDisplay();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if ( this.selectedServer.ip == null ) return;

        /**
         * Make an array of strings which contains server data
         */
        ArrayList<String> serverInfo = new ArrayList<>();
        serverInfo.add( "Имя: " + this.selectedServer.name );
        serverInfo.add( "Город: " + this.selectedServer.city );
        serverInfo.add( "IP: " + this.selectedServer.ip );
        serverInfo.add( "isp: " + this.selectedServer.isp );
        serverInfo.add( "Провайдер: " + this.selectedServer.organization );
        serverInfo.add( "Страна: " + this.selectedServer.countryName );
        serverInfo.add( "Прокси: " + (this.selectedServer.proxy ? "Да" : "Нет") );

        /**
         * Calculated values for graphics part
         */
        int labelHeight = 10;
        int halfHeight = this.height / 2;
        int rectHeight = labelHeight * serverInfo.size();
        int rectWidth  = 120;
        int rectY = super.height / 2 - ( rectHeight / 2 );
        int rectX = 5;

        // Draw transparent panel
        drawRect(
                rectX,
                rectY - 5, // - 5 padding for text
                rectX + rectWidth,
                rectY + rectHeight + 5, // + 5 also padding for text
                new Color(0, 0, 0, 170).getRGB()
        );

        /**
         * Draw text from array
         */
        for (int i = 0; i < serverInfo.size(); i++)
        {
            int startPoint = (halfHeight + labelHeight * i) - rectHeight / 2;

            mc.fontRendererObj.drawString(
                    serverInfo.get(i),
                    rectX + 5,
                    startPoint,
                    new Color(255, 255, 255, 255).getRGB()
            );
        }
    }

    @Override
    protected void refreshServerList()
    {
        this.mc.displayGuiScreen(new GuiServerList(this.parentScreen));
    }
}
