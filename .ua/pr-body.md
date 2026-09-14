Adds the Understand-Anything knowledge graph for the frisboo-build-logic repository.

## Summary

- `.ua/knowledge-graph.json` — 72 file-level nodes, 203 edges, 6 layers, 12-step guided tour
- `.ua/fingerprints.json` — content fingerprints for all 139 analyzed files
- `.ua/meta.json` — analysis metadata (git commit, timestamp)
- `.gitignore` — excludes the local `.ua-plugin/` toolchain shim

## Graph structure

| Layer | Contents |
|---|---|
| Root Build & Settings | settings, root build script, CI workflow, gradle wrapper |
| Version Catalog | `libs.versions.toml` pinning all plugin/library/bundle versions |
| Build Logic (included build) | Kotlin & quality Gradle conventions, catalog constants generator |
| Convention Plugin | Plugin entry point, extensions, managers, utilities |
| Integration Tests | Gradle TestKit plugin tests |
| Configuration & Quality Rules | License header, detekt config, editorconfig |

## Edge types

`depends_on` (89), `configures` (41), `contains` (27), `applies_convention` (5), `calls` (13), `reads_from` (6), `tested_by` (2), `uses_templates` (2), `includes_build` (1), `includes_project` (2), `registers_plugin` (1), `registers_task` (1), `generates` (1), `triggers` (1), `invokes` (1), `documents` (1), `related` (8), `used_by` (1)

## Validation

Inline validator: 0 issues, 0 warnings. All edge references resolved, every file node assigned to exactly one layer, all tour references valid.