//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `Montecarlo.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public interface MasterPrx extends com.zeroc.Ice.ObjectPrx
{
    default float calculatePi(int totalPoints, boolean isTest)
    {
        return calculatePi(totalPoints, isTest, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default float calculatePi(int totalPoints, boolean isTest, java.util.Map<String, String> context)
    {
        return _iceI_calculatePiAsync(totalPoints, isTest, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Float> calculatePiAsync(int totalPoints, boolean isTest)
    {
        return _iceI_calculatePiAsync(totalPoints, isTest, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Float> calculatePiAsync(int totalPoints, boolean isTest, java.util.Map<String, String> context)
    {
        return _iceI_calculatePiAsync(totalPoints, isTest, context, false);
    }

    /**
     * @hidden
     * @param iceP_totalPoints -
     * @param iceP_isTest -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Float> _iceI_calculatePiAsync(int iceP_totalPoints, boolean iceP_isTest, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Float> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "calculatePi", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeInt(iceP_totalPoints);
                     ostr.writeBool(iceP_isTest);
                 }, istr -> {
                     float ret;
                     ret = istr.readFloat();
                     return ret;
                 });
        return f;
    }

    default boolean addWorker(String name, WorkerPrx w)
    {
        return addWorker(name, w, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean addWorker(String name, WorkerPrx w, java.util.Map<String, String> context)
    {
        return _iceI_addWorkerAsync(name, w, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> addWorkerAsync(String name, WorkerPrx w)
    {
        return _iceI_addWorkerAsync(name, w, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> addWorkerAsync(String name, WorkerPrx w, java.util.Map<String, String> context)
    {
        return _iceI_addWorkerAsync(name, w, context, false);
    }

    /**
     * @hidden
     * @param iceP_name -
     * @param iceP_w -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_addWorkerAsync(String iceP_name, WorkerPrx iceP_w, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "addWorker", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_name);
                     ostr.writeProxy(iceP_w);
                 }, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    default boolean removeWorker(String name)
    {
        return removeWorker(name, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean removeWorker(String name, java.util.Map<String, String> context)
    {
        return _iceI_removeWorkerAsync(name, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> removeWorkerAsync(String name)
    {
        return _iceI_removeWorkerAsync(name, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> removeWorkerAsync(String name, java.util.Map<String, String> context)
    {
        return _iceI_removeWorkerAsync(name, context, false);
    }

    /**
     * @hidden
     * @param iceP_name -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_removeWorkerAsync(String iceP_name, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "removeWorker", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_name);
                 }, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    default int getWorkerCount()
    {
        return getWorkerCount(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default int getWorkerCount(java.util.Map<String, String> context)
    {
        return _iceI_getWorkerCountAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getWorkerCountAsync()
    {
        return _iceI_getWorkerCountAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getWorkerCountAsync(java.util.Map<String, String> context)
    {
        return _iceI_getWorkerCountAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> _iceI_getWorkerCountAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getWorkerCount", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     int ret;
                     ret = istr.readInt();
                     return ret;
                 });
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MasterPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MasterPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MasterPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static MasterPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static MasterPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static MasterPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, MasterPrx.class, _MasterPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default MasterPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (MasterPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default MasterPrx ice_adapterId(String newAdapterId)
    {
        return (MasterPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default MasterPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (MasterPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default MasterPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (MasterPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default MasterPrx ice_invocationTimeout(int newTimeout)
    {
        return (MasterPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default MasterPrx ice_connectionCached(boolean newCache)
    {
        return (MasterPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default MasterPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (MasterPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default MasterPrx ice_secure(boolean b)
    {
        return (MasterPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default MasterPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (MasterPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default MasterPrx ice_preferSecure(boolean b)
    {
        return (MasterPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default MasterPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (MasterPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default MasterPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (MasterPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default MasterPrx ice_collocationOptimized(boolean b)
    {
        return (MasterPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default MasterPrx ice_twoway()
    {
        return (MasterPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default MasterPrx ice_oneway()
    {
        return (MasterPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default MasterPrx ice_batchOneway()
    {
        return (MasterPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default MasterPrx ice_datagram()
    {
        return (MasterPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default MasterPrx ice_batchDatagram()
    {
        return (MasterPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default MasterPrx ice_compress(boolean co)
    {
        return (MasterPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default MasterPrx ice_timeout(int t)
    {
        return (MasterPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default MasterPrx ice_connectionId(String connectionId)
    {
        return (MasterPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default MasterPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (MasterPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Demo::Master";
    }
}
