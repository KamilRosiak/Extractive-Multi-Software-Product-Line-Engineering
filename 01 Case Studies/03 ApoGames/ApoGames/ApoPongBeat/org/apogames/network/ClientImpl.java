package org.apogames.network;

public class ClientImpl implements ClientPreProcessor, DataTransferListener {
	
	private SimpleClient client = null;
	private long startTime = 0;
	
	private long curTime = 0;
	
	private int clientNumber;
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 2; i++) {
			new ClientImpl((i + 1)).run();
		}
	}
	
	public ClientImpl(int client) {
		this.clientNumber = client;
	}

	private void run() throws Exception {
		client = new SimpleClient("127.0.0.1", SimpleServer.DEFAULT_PORT, false, null);
		client.addPreProcessor(this);
		client.addListener(this);
		client.connect();
		startTime = System.currentTimeMillis();
		this.curTime = this.startTime;
	}

	public void afterReceiving() {
		long curTime = System.currentTimeMillis();
		if (curTime - startTime > 4000) {
			client.disconnect();
		} else {
			//System.out.println(curTime - this.curTime);
			this.curTime = curTime;
		}
	}

	public void beforeReceiving() {
	}

	public void beforeSending() {
		DataContainer dc = new DataContainer();
		float f = (float) (Math.random() * 10f);
		dc.add(f);
		client.setContainers(new DataContainer[] { dc });
	}

	public DataContainer[] dataReceived(DataContainer data, int messageType) {
		if (data != null) {
			if (messageType == MessageTypes.OBJ_TRANSFER) {
				if (data.hasData() && data.getType() == DataContainer.TYPE_STRING) {
					System.out.println("Server anwortet mit: " + data.getNextString() + " bei client " + this.clientNumber);
				}
				if (data.hasData() && data.getType() == DataContainer.TYPE_FLOAT) {
					//System.out.println("Und bedankt sich damit für: " + data.getNextFloat());
				}
			}
		}
		return null;
	}

	public void dataReceivedEnd() {
	}
}
