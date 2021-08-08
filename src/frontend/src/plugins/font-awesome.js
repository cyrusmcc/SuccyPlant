import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
    faHome,
    faUser,
    faSignInAlt,
    faSignOutAlt
} from "@fortawesome/free-solid-svg-icons";

library.add(faHome, faUser, faSignInAlt, faSignOutAlt);

export { FontAwesomeIcon };