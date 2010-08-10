/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.osgi.installer.impl.tasks;

import java.io.IOException;

import org.apache.sling.osgi.installer.impl.OsgiInstallerContext;
import org.apache.sling.osgi.installer.impl.OsgiInstallerTask;
import org.apache.sling.osgi.installer.impl.RegisteredResource;
import org.apache.sling.osgi.installer.impl.config.ConfigurationPid;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/** Base class for configuration-related tasks */
abstract class AbstractConfigTask extends OsgiInstallerTask {

    protected final ConfigurationPid pid;
    protected final RegisteredResource resource;
    
    AbstractConfigTask(RegisteredResource r) {
        resource = r;
        pid = (ConfigurationPid)r.getAttributes().get(RegisteredResource.CONFIG_PID_ATTRIBUTE);
        if(pid == null) {
            throw new IllegalArgumentException("RegisteredResource does not have CONFIG_PID_ATTRIBUTE: " + r);
        }
    }
    
    protected Configuration getConfiguration(ConfigurationAdmin ca, 
            ConfigurationPid cp, boolean createIfNeeded, OsgiInstallerContext ocs)
    throws IOException, InvalidSyntaxException
    {
        Configuration result = null;

        if (cp.getFactoryPid() == null) {
            result = ca.getConfiguration(cp.getConfigPid(), null);
        } else {
            Configuration configs[] = ca.listConfigurations(
                "(|(" + ConfigInstallTask.ALIAS_KEY
                + "=" + cp.getFactoryPid() + ")(.alias_factory_pid=" + cp.getFactoryPid()
                + "))");

            if (configs == null || configs.length == 0) {
                if(createIfNeeded) {
                    result = ca.createFactoryConfiguration(cp.getConfigPid(), null);
                }
            } else {
                result = configs[0];
            }
        }

        return result;
    }
    
    @Override
    public String toString() {
        return getClass().getName() + ": " + resource;
    }

    @Override
    public boolean isExecutable(OsgiInstallerContext ctx) throws Exception {
        return ctx.getConfigurationAdmin() != null;
    }
}
