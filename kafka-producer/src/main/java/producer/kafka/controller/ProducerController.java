package producer.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import producer.kafka.sender.Sender;

@RestController
@RequestMapping("/producer")
public class ProducerController {

	@Autowired
	private Sender sender;

	@RequestMapping(value = "/send/{topic}", method = RequestMethod.POST)
	public String sendMessage(@PathVariable("topic") String topic, @RequestBody String message) {
		sender.send(topic, message);
		return "Request posted successfully. You will get notified soon.";
	}
}