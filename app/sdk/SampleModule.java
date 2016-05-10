package sdk;

import com.google.inject.AbstractModule;
import sdk.sample.PriorityListDataSource;
import sdk.sample.WorkOrderDataSource;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleModule extends AbstractModule {
    @Override
    protected void configure() {
        AppTree.registerListDataSourceWithName("priorities", new PriorityListDataSource());
        AppTree.registerDataSourceWithName("workorders", new WorkOrderDataSource());
    }
}
