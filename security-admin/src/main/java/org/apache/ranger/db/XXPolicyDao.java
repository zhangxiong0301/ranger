/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ranger.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.collections.ListUtils;
import org.apache.ranger.common.db.BaseDao;
import org.apache.ranger.entity.XXPolicy;
import org.apache.ranger.plugin.model.RangerSecurityZone;
import org.springframework.stereotype.Service;

/**
 */

@Service
public class XXPolicyDao extends BaseDao<XXPolicy> {
	/**
	 * Default Constructor
	 */
	public XXPolicyDao(RangerDaoManagerBase daoManager) {
		super(daoManager);
	}

	public long getCountById(Long policyId) {
		return getEntityManager()
					.createNamedQuery("XXPolicy.countById", Long.class)
					.setParameter("policyId", policyId)
					.getSingleResult();
	}

	public XXPolicy findByNameAndServiceId(String polName, Long serviceId) {
		return findByNameAndServiceIdAndZoneId(polName, serviceId, RangerSecurityZone.RANGER_UNZONED_SECURITY_ZONE_ID);
	}

	public XXPolicy findByNameAndServiceIdAndZoneId(String polName, Long serviceId, Long zoneId) {
		if (polName == null || serviceId == null) {
			return null;
		}

		XXPolicy ret;

		try {
			ret = getEntityManager()
					.createNamedQuery("XXPolicy.findByNameAndServiceIdAndZoneId", tClass)
					.setParameter("polName", polName).setParameter("serviceId", serviceId).setParameter("zoneId", zoneId)
					.getSingleResult();

		} catch (NoResultException e) {
			ret = null;
		}

		return ret;
	}

	public XXPolicy findByPolicyName(String polName) {
		if (polName == null) {
			return null;
		}
		try {
			XXPolicy xPol = getEntityManager().createNamedQuery("XXPolicy.findByPolicyName", tClass)
					.setParameter("polName", polName).getSingleResult();
			return xPol;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<XXPolicy> findByServiceId(Long serviceId) {
		if (serviceId == null) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager()
					.createNamedQuery("XXPolicy.findByServiceId", tClass)
					.setParameter("serviceId", serviceId).getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}

	public List<Long> findPolicyIdsByServiceId(Long serviceId) {
		List<Long> ret = new ArrayList<Long>();
		try {
			ret = getEntityManager()
					.createNamedQuery("XXPolicy.findPolicyIdsByServiceId", Long.class)
					.setParameter("serviceId", serviceId).getResultList();
		} catch (Exception e) {
		}
		return ret;
	}

	public Long getMaxIdOfXXPolicy() {
		try {
			return (Long) getEntityManager().createNamedQuery("XXPolicy.getMaxIdOfXXPolicy").getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<XXPolicy> findByResourceSignatureByPolicyStatus(String serviceName, String policySignature, Boolean isPolicyEnabled) {
		if (policySignature == null || serviceName == null || isPolicyEnabled == null) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager().createNamedQuery("XXPolicy.findByResourceSignatureByPolicyStatus", tClass)
					.setParameter("resSignature", policySignature)
					.setParameter("serviceName", serviceName)
					.setParameter("isPolicyEnabled", isPolicyEnabled)
					.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}

	public List<XXPolicy> findByResourceSignature(String serviceName, String policySignature) {
		if (policySignature == null || serviceName == null) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager().createNamedQuery("XXPolicy.findByResourceSignature", tClass)
					.setParameter("resSignature", policySignature)
					.setParameter("serviceName", serviceName)
					.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}

	public List<XXPolicy> findByServiceDefId(Long serviceDefId) {
		if(serviceDefId == null) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager().createNamedQuery("XXPolicy.findByServiceDefId", tClass)
					.setParameter("serviceDefId", serviceDefId).getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}

	public void updateSequence() {
		Long maxId = getMaxIdOfXXPolicy();

		if(maxId == null) {
			return;
		}

		updateSequence("X_POLICY_SEQ", maxId + 1);
	}
	public List<XXPolicy> findByUserId(Long userId) {
		if(userId == null || userId.equals(Long.valueOf(0L))) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager()
					.createNamedQuery("XXPolicy.findByUserId", tClass)
					.setParameter("userId", userId).getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}
	public List<XXPolicy> findByGroupId(Long groupId) {
		if(groupId == null || groupId.equals(Long.valueOf(0L))) {
			return new ArrayList<XXPolicy>();
		}
		try {
			return getEntityManager()
					.createNamedQuery("XXPolicy.findByGroupId", tClass)
					.setParameter("groupId", groupId).getResultList();
		} catch (NoResultException e) {
			return new ArrayList<XXPolicy>();
		}
	}

	public List<Long> findPolicyIdsByServiceNameAndZoneId(String serviceName, Long zoneId) {
		List<Long> ret = new ArrayList<Long>();
		try {
			ret = getEntityManager()
					.createNamedQuery("XXPolicy.findPolicyIdsByServiceNameAndZoneId", Long.class)
					.setParameter("serviceName", serviceName)
					.setParameter("zoneId", zoneId)
					.getResultList();
		} catch (Exception e) {
		}
		return ret;
	}

	public List<XXPolicy> findByRoleId(Long roleId) {
		List<XXPolicy> ret = ListUtils.EMPTY_LIST;
		if (roleId != null) {
			try {
				ret = getEntityManager().createNamedQuery("XXPolicy.findByRoleId", tClass)
						.setParameter("roleId", roleId)
						.getResultList();
			} catch (NoResultException excp) {
			}
		}
		return ret;
	}
	public List<Long> findServiceIdsByRoleId(Long roleId) {
		List<Long> ret = ListUtils.EMPTY_LIST;
		if (roleId != null) {
			try {
				ret = getEntityManager().createNamedQuery("XXPolicy.findServiceIdsByRoleId", Long.class)
						.setParameter("roleId", roleId)
						.getResultList();
			} catch (NoResultException excp) {
			}
		}
		return ret;
	}

	public long findRoleRefPolicyCount(String roleName, Long serviceId) {
		long ret = -1;
		try {
			return getEntityManager()
					.createNamedQuery("XXPolicy.findRoleRefPolicyCount", Long.class)
					.setParameter("serviceId", serviceId)
					.setParameter("roleName", roleName).getSingleResult();
		} catch (Exception e) {
		}
		return ret;
	}
}