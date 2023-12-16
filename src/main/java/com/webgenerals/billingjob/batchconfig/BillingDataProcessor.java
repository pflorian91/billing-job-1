package com.webgenerals.billingjob.batchconfig;

import com.webgenerals.billingjob.model.BillingData;
import com.webgenerals.billingjob.model.ReportingData;
import com.webgenerals.billingjob.service.PricingService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class BillingDataProcessor implements ItemProcessor<BillingData, ReportingData> {

	private final PricingService pricingService;

	@Value("${spring.cellular.spending.threshold:150}")
	private float spendingThreshold;

	public BillingDataProcessor(PricingService pricingService) {
		this.pricingService = pricingService;
	}

	@Override
	public ReportingData process(BillingData item) {
		double billingTotal = item.dataUsage() * pricingService.getDataPricing() +
						item.callDuration() * pricingService.getCallPricing() +
						item.smsCount() * pricingService.getSmsPricing();

		if (billingTotal < spendingThreshold) {
			return null;
		}

		return new ReportingData(item, billingTotal);
	}
}
