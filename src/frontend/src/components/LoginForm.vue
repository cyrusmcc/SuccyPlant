<template>
  <div class="card" id="loginCard">
    <form action="#">
      <fieldset>
        <p>Sign in</p>
        <div class="form-in">
          <label class="form-label" for="usernameInput">Username</label>
          <input aria-describedby="usernameHelp" class="form-control" id="usernameInput" name="username"
            type="text" required>
        </div>
        <div class="form-in">
          <label class="form-label" for="passwordInput">Password</label>
          <input class="form-control" id="passwordInput" name="password"
            type="text" required>
        </div>

        <button class="button-primary" type="submit" id="loginButton">Log in</button>

        <router-link to="/register" class="button-primary" id="registerButton">
          Register</router-link>


      </fieldset>
    </form>

  </div>
</template>

<script>
export default {

}
</script>

<style scoped lang="scss">
  #loginCard {
    color: $lightShade;
    text-align: center;
  }

  fieldset{
    position: relative;
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  .form-in {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    font-size: 0.8rem;
    padding: 10px 0 10px 0;
  }

  .form-label {
    padding-bottom: 5px;
  }

  .form-control{
    background-color: $darkShade;
    color: $lightShade;
    border: none;
    padding: 10px;
    border-radius: 3px;
  }

  #loginButton {
    margin-top: 5px;
    align-self: flex-start;
    width: 100%;
    font-size: 0.75rem;
  } 

  #registerButton {
    margin-top: 15px;
    font-size: 0.75rem;
    width: 100%;
    padding: 7px 0;
    }

</style>