package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.type.PermissionType;
import com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codingshuttle.youtube.hospitalManagement.entity.type.PermissionType.*;
import static com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType.*;

public class RolePermissionMapping {

    // Corrected the value type to Set<PermissionType>
    private static final Map<RoleType, Set<PermissionType>> map = Map.of(

            // 1. PATIENT Role Permissions
            PATIENT, Set.of(
                    PATIENT_READ,
                    APPOINTMENT_READ,
                    APPOINTMENT_WRITE
            ),

            // 2. DOCTOR Role Permissions
            DOCTOR, Set.of(
                    PATIENT_READ,
                    APPOINTMENT_READ,
                    APPOINTMENT_WRITE,
//                    APPOINTMENT_DELETE,
                    REPORT_VIEW
            ),

            // 3. ADMIN Role Permissions (All available permissions)
            ADMIN, Stream.of(PermissionType.values())
                    .collect(Collectors.toSet())

//            ,

//            ADMIN, Set.of(PATIENT_READ,
//                    PATIENT_WRITE,
//                    APPOINTMENT_READ,
//                    APPOINTMENT_WRITE,
//                    APPOINTMENT_DELETE,
//                    USER_MANAGER,
//                    REPORT_VIEW
//            )
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}