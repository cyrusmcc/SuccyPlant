<template>
  <div>
    {{ formattedDate }}
  </div>
</template>

<script>
export default {
  date() {
    return {
      formattedDate: "",
    };
  },
  props: ["date", "format"],
  created() {
    if (this.format == "full") {
      const date = new Date(this.date),
        locale = "en-us",
        month = date.toLocaleString(locale, { month: "short" }),
        year = date.toLocaleString(locale, { year: "numeric" }),
        day = date.toLocaleString(locale, { day: "numeric" }),
        formatted = day + " " + month + " " + year;
      this.formattedDate = formatted;
    }
    if (this.format == "month") {
      const date = new Date(this.date),
        locale = "en-us",
        month = date.toLocaleString(locale, { month: "short" }),
        year = date.toLocaleString(locale, { year: "numeric" }),
        formatted = month + " " + year;
      this.formattedDate = formatted;
    }
  },
};
</script>
<style></style>
