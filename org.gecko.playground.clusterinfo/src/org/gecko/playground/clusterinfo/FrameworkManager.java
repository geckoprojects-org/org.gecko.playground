package org.gecko.playground.clusterinfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.dto.BundleDTO;
import org.osgi.framework.dto.FrameworkDTO;
import org.osgi.framework.dto.ServiceReferenceDTO;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.startlevel.dto.BundleStartLevelDTO;
import org.osgi.framework.startlevel.dto.FrameworkStartLevelDTO;
import org.osgi.service.clusterinfo.FrameworkNodeStatus;
import org.osgi.service.clusterinfo.NodeStatus;

public class FrameworkManager implements FrameworkNodeStatus {
	
	private final BundleContext bctx;
	private final NodeStatus monitor = new MBeanMonitor();

	public FrameworkManager(BundleContext bctx) {
		this.bctx = bctx;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.NodeStatus#getMetrics(java.lang.String[])
	 */
	@Override
	public Map<String, Object> getMetrics(String... names) {
		return monitor.getMetrics(names);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getBundle(long)
	 */
	@Override
	public BundleDTO getBundle(long id) throws Exception {
		return bctx.getBundle(id).adapt(BundleDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getBundleHeaders(long)
	 */
	@Override
	public Map<String, String> getBundleHeaders(long id) throws Exception {
		Map<String,String> map = new HashMap<>();
		Dictionary<String,?> headers = bctx.getBundle(id).getHeaders();
		Enumeration<String> e = headers.keys();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			Object v = headers.get(key);
			map.put(key, v != null ? v.toString() : null);
		}
		return map;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getBundles()
	 */
	@Override
	public Collection<BundleDTO> getBundles() throws Exception {
		FrameworkDTO framework = bctx.getBundle(0).adapt(FrameworkDTO.class);
		return framework.bundles;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getBundleStartLevel(long)
	 */
	@Override
	public BundleStartLevelDTO getBundleStartLevel(long id) throws Exception {
		return bctx.getBundle(id).adapt(BundleStartLevelDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getBundleState(long)
	 */
	@Override
	public int getBundleState(long id) throws Exception {
		return bctx.getBundle(id).getState();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getFrameworkStartLevel()
	 */
	@Override
	public FrameworkStartLevelDTO getFrameworkStartLevel() throws Exception {
		return bctx.getBundle(0).adapt(FrameworkStartLevelDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getServiceReference(long)
	 */
	@Override
	public ServiceReferenceDTO getServiceReference(long id) throws Exception {
		for(ServiceReferenceDTO r : getServiceReferences()){
			if(r.id == id)
				return r;
		}
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getServiceReferences()
	 */
	@Override
	public Collection<ServiceReferenceDTO> getServiceReferences() throws Exception {
		FrameworkDTO framework = bctx.getBundle(0).adapt(FrameworkDTO.class);
		return framework.services;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#getServiceReferences(java.lang.String)
	 */
	@Override
	public Collection<ServiceReferenceDTO> getServiceReferences(String filter) throws Exception {
		Filter f = bctx.createFilter(filter);
		List<ServiceReferenceDTO> filtered = new ArrayList<>();
		for(ServiceReferenceDTO r : getServiceReferences()){
			if(f.matches(r.properties)){
				filtered.add(r);
			}
		}
		return filtered; 
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#installBundle(java.lang.String)
	 */
	@Override
	public BundleDTO installBundle(String location) throws Exception {
		Bundle b = bctx.installBundle(location);
		return b.adapt(BundleDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#setBundleStartLevel(long, int)
	 */
	@Override
	public void setBundleStartLevel(long id, int startLevel) throws Exception {
		Bundle b = bctx.getBundle(id);
		if(b == null)
			return;
		
		BundleStartLevel bsl = b.adapt(BundleStartLevel.class);
		bsl.setStartLevel(startLevel);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#setFrameworkStartLevel(org.osgi.framework.startlevel.dto.FrameworkStartLevelDTO)
	 */
	@Override
	public void setFrameworkStartLevel(FrameworkStartLevelDTO startLevel) throws Exception {
		Bundle fw = bctx.getBundle(0);
		FrameworkStartLevel fwsl = fw.adapt(FrameworkStartLevel.class);
		fwsl.setInitialBundleStartLevel(startLevel.initialBundleStartLevel);
		fwsl.setStartLevel(startLevel.startLevel);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#startBundle(long)
	 */
	@Override
	public void startBundle(long id) throws Exception {
		Bundle b = bctx.getBundle(id);
		if(b == null)
			return;
		
		b.start();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#startBundle(long, int)
	 */
	@Override
	public void startBundle(long id, int options) throws Exception {
		Bundle b = bctx.getBundle(id);
		if(b == null)
			return;
		
		b.start(options);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#stopBundle(long)
	 */
	@Override
	public void stopBundle(long id) throws Exception {
		Bundle b = bctx.getBundle(id);
		if(b == null)
			return;
		
		b.stop();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#stopBundle(long, int)
	 */
	@Override
	public void stopBundle(long id, int options) throws Exception {
		Bundle b = bctx.getBundle(id);
		if(b == null)
			return;
		
		b.stop(options);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#uninstallBundle(long)
	 */
	@Override
	public BundleDTO uninstallBundle(long id) throws Exception {
		Bundle b  = bctx.getBundle(id);
		if(b == null)
			return null;
		
		b.uninstall();
		return b.adapt(BundleDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#updateBundle(long)
	 */
	@Override
	public BundleDTO updateBundle(long id) throws Exception {
		Bundle b  = bctx.getBundle(id);
		if(b == null)
			return null;
		
		b.update();
		return b.adapt(BundleDTO.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.osgi.service.clusterinfo.FrameworkManager#updateBundle(long, java.lang.String)
	 */
	@Override
	public BundleDTO updateBundle(long id, String url) throws Exception {
		Bundle b  = bctx.getBundle(id);
		if(b == null)
			return null;
		
		URL u = new URL(url);
		b.update(u.openStream());
		return b.adapt(BundleDTO.class);
	}

}
