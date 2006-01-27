/******************************************************************************* * Copyright (c) 2006 IBM Corporation and others. * All rights reserved. This program and the accompanying materials * are made available under the terms of the Eclipse Public License v1.0 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html * * Contributors: *     IBM Corporation - initial API and implementation ******************************************************************************/package org.eclipse.ui.internal.services;import java.util.HashMap;import java.util.HashSet;import java.util.Iterator;import java.util.Map;import java.util.Set;import org.eclipse.ui.ISourceProvider;import org.eclipse.ui.services.IDisposable;/** * <p> * A service holding all of the registered source providers. * </p> * <p> * This class is not intended for use outside of the * <code>org.eclipse.ui.workbench</code> plug-in. * </p> * <p> * <strong>EXPERIMENTAL</strong>. This class or interface has been added as * part of a work in progress. There is a guarantee neither that this API will * work nor that it will remain the same. Please do not use this API without * consulting with the Platform/UI team. * </p> *  * @since 3.2 */public final class SourceProviderService implements ISourceProviderService,		IDisposable {	/**	 * The source providers registered with this service. This value is never	 * <code>null</code>. This is a map of the source name ({@link String})	 * to the source provider ({@link ISourceProvider}).	 */	private final Map sourceProvidersByName = new HashMap();	/**	 * All of the source providers registered with this service. This value is	 * never <code>null</code>.	 */	private final Set sourceProviders = new HashSet();	public final void dispose() {		final Iterator sourceProviderItr = sourceProviders.iterator();		while (sourceProviderItr.hasNext()) {			final ISourceProvider sourceProvider = (ISourceProvider) sourceProviderItr					.next();			sourceProvider.dispose();		}		sourceProviders.clear();		sourceProvidersByName.clear();	}	public final ISourceProvider getSourceProvider(final String sourceName) {		return (ISourceProvider) sourceProvidersByName.get(sourceName);	}	public final ISourceProvider[] getSourceProviders() {		return (ISourceProvider[]) sourceProviders				.toArray(new ISourceProvider[sourceProvidersByName.size()]);	}	public final void registerProvider(final ISourceProvider sourceProvider) {		if (sourceProvider == null) {			throw new NullPointerException("The source provider cannot be null"); //$NON-NLS-1$		}		final String[] sourceNames = sourceProvider.getProvidedSourceNames();		for (int i = 0; i < sourceNames.length; i++) {			final String sourceName = sourceNames[i];			sourceProvidersByName.put(sourceName, sourceProvider);		}		sourceProviders.add(sourceProvider);	}}