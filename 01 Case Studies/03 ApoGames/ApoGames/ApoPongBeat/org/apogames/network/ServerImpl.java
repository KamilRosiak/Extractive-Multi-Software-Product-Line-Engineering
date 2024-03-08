package org.apogames.network;

public class ServerImpl implements DataTransferListener, ClientLoginListener, ClientLogoutListener {
	private SimpleServer server = null;
	private int clientCnt = 0;

	public static void main(String[] args) {
		new ServerImpl().run();
	}

	private void run() {
		server = new SimpleServer(SimpleServer.DEFAULT_PORT, SimpleServer.UDP_DEFAULT_PORT, false, "my first server");
		server.addListener(this);
		server.addLoginListener(this);
		server.addLogoutListener(this);
	}

	public DataContainer[] dataReceived(DataContainer data, int messageType) {
		float content = data.getNextFloat();
		System.out.println("Client " + data.getClientInfo().getID() + " sagt: " + content);

		DataContainer dc = new DataContainer();
		dc.add("Client " + data.getClientInfo().getID() + " schickt: " + content);
		dc.add(content);
		return new DataContainer[] { dc };
	}

	public void dataReceivedEnd() {
	}

	public DataContainer loggedIn(ClientInfo ci, DataContainer dc) {
		System.out.println("Client eingeloggt: " + ci.getID());
		clientCnt++;
		DataContainer dcr = new DataContainer();
		dcr.add("Client eingeloggt: " + ci.getID());
		return dcr;
	}

	public DataContainer loggedOut(ClientInfo ci) {
		System.out.println("Client ausgeloggt: " + ci.getID());
		clientCnt--;
		if (clientCnt == 0) {
			System.out.println("Alle Clients weg...ich will auch nicht mehr!");
			server.shutDown();
		}
		return null;
	}
}
