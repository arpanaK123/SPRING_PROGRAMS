package com.bridgeit.DI;

public class MyDIApplication implements Consumer{
	private MessageService messageService;

	
	public MyDIApplication(MessageService messageService) {
		super();
		this.messageService = messageService;
	}


	@Override
	public void processMessage(String message, String receiver) {
		this.messageService.sendMessage(message, receiver);
	}

}
