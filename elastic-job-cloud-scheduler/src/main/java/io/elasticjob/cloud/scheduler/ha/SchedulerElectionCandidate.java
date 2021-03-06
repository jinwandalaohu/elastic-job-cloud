/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.elasticjob.cloud.scheduler.ha;

import io.elasticjob.cloud.scheduler.mesos.SchedulerService;
import io.elasticjob.cloud.reg.base.ElectionCandidate;
import io.elasticjob.cloud.exception.JobSystemException;
import io.elasticjob.cloud.reg.base.CoordinatorRegistryCenter;

/**
 * 调度器选举候选人.
 *
 * @author caohao
 */
public final class SchedulerElectionCandidate implements ElectionCandidate {
    
    private final CoordinatorRegistryCenter regCenter;
    
    private SchedulerService schedulerService;
    
    public SchedulerElectionCandidate(final CoordinatorRegistryCenter regCenter) {
        this.regCenter = regCenter;
    }
    
    @Override
    public void startLeadership() throws Exception {
        try {
            schedulerService = new SchedulerService(regCenter);
            schedulerService.start();
            //CHECKSTYLE:OFF
        } catch (final Throwable throwable) {
            throw new JobSystemException(throwable);
        }
    }
    
    @Override
    public void stopLeadership() {
        schedulerService.stop();
    }
}
