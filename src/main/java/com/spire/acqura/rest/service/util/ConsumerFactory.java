package com.spire.acqura.rest.service.util;

import com.spire.acqura.rest.service.consumers.GISearchConsumer;
import com.spire.acqura.rest.service.consumers.CandidateConsumer;

public class ConsumerFactory {

	public GISearchConsumer getGISearchConsumer() {
		return new GISearchConsumer();
	}

	public CandidateConsumer getNewCandidateConsumer() {
		return new CandidateConsumer();
	}

}
