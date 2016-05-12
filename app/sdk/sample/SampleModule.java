package sdk.sample;

import com.google.inject.AbstractModule;
import sdk.AppTree;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleModule extends AbstractModule {
    @Override
    protected void configure() {
        AppTree.registerListDataSourceWithName("priorities", new PriorityListDataSource());
        AppTree.registerDataSourceWithName("workorders", new WorkOrderDataSource());
        AppTree.registerAuthenticationSource(new AuthenticationDataSource());

    }
}
