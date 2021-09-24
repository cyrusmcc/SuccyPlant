import axiosInstance from "./api";
import TokenService from "./token.service";

const setup = (store) => {
  axiosInstance.interceptors.request.use(
    (config) => {
      const token = TokenService.getLocalAccessToken();
      if (token) {
        console.log(TokenService.getLocalAccessToken());
        config.headers["Authorization"] = 'Bearer ' + token;  // for Spring Boot back-end
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInstance.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;

      if (err.response) {

        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;

          //console.log("401 error");
          //this.$router.push("/login");
            
          try {
            const rs = await axiosInstance.post("/auth/refreshToken", {
              refreshToken: TokenService.getLocalRefreshToken(),
            });

            const { accessToken } = rs.data;

            store.dispatch('auth/refreshToken', accessToken);
            TokenService.updateLocalAccessToken(accessToken);

            return axiosInstance(originalConfig);
          } catch (_error) {
            return Promise.reject(_error);
          } 
        }
      }

      return Promise.reject(err);
    }
  );
};

export default setup;